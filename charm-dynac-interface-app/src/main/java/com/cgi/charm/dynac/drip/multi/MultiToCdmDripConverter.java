package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.cdm.drip.*;
import com.cgi.charm.dynac.multi.JustificationLineType;

/**
 * @author CHARM CGI TEAM
 */
class MultiToCdmDripConverter implements DmsMultiParserHandler {
    private JustificationLineType justificationLineType;
    private DripImageAttr result;
    private Line currentLine;
    private int currentPictogramIndex;

    /**
     * Constructor
     */
    public MultiToCdmDripConverter() {
        result = new DripImageAttr();
        result.setDuration(-1);

        currentLine = new Line();

        justificationLineType = JustificationLineType.LEFT;
    }

    @Override
    public void justificationLine(JustificationLineType justificationLineType) {
        this.justificationLineType = justificationLineType;
    }

    @Override
    public void text(String text) {
        if (result.getText() == null) {
            result.setText(new Text());
        }
        currentLine.setTextSpecific(currentLine.getTextSpecific() != null ?
                currentLine.getTextSpecific() + text : text);
        currentLine.setTextAlignment(toTextAlignment(justificationLineType));
    }

    private TextAlignment toTextAlignment(
            JustificationLineType justificationLineType) {
        switch (justificationLineType) {
            case LEFT:
                return TextAlignment.LEFT;
            case RIGHT:
                return TextAlignment.RIGHT;
            case CENTER:
            default:
                return TextAlignment.CENTER;
        }
    }

    @Override
    public void newline(Integer lineSpacing) {
        int amount = lineSpacing == null ? 1 : lineSpacing;
        for (int i = 0; i < amount; i++) {
            flushLine();
        }
    }

    private void flushLine() {

        // TODO should be possible to properly represent empty lines in CDM
        if (currentLine.getTextSpecific() == null) {
            text("");
        }

        result.getText().getLine().add(currentLine);

        currentLine = new Line();
    }

    public DripImageAttr getResult() {
        if (result.getText() != null) {
            flushLine();
        }

        return result;
    }

    @Override
    public void graphic(int dmsGraphicNumber, int x, int y,
                        String dmsGraphicVersionID) {
        result.getPictogram()
                .add(new Pictogram(dmsGraphicNumber, currentPictogramIndex++));
    }

    @Override
    public void hexChar(int characterIndex) {
        String text = new String(Character.toChars(characterIndex));
        currentLine.setTextSpecific(currentLine.getTextSpecific() != null ?
                currentLine.getTextSpecific() + text : text);
    }
}
