package com.cgi.charm.dynac.drip.multi;

import com.cgi.charm.cdm.drip.DripImageAttr;
import com.cgi.charm.cdm.drip.Line;
import com.cgi.charm.cdm.drip.Pictogram;
import com.cgi.charm.cdm.drip.TextAlignment;

/**
 * @author CHARM CGI TEAM
 */
class CdmDripToMultiConverter {
    private StringBuilder result = new StringBuilder();

    /**
     * Convert the image, the result can be obtained with {@link #getResult()}.
     *
     * @param image the image
     */
    public void convert(DripImageAttr image) {

        // [g4,1,1]
        if (image.getPictogram() != null) {
            for (Pictogram pictogram : image.getPictogram()) {
                appendPictogram(pictogram);
            }
        }

        if (image.getText() != null) {
            boolean notFirst = false;
            for (Line line : image.getText().getLine()) {
                if (notFirst) {
                    appendNewline();
                }
                appendLine(line);

                notFirst = true;
            }
        }
    }

    private void appendPictogram(Pictogram pictogram) {
        result.append("[g").append(pictogram.getPictogramNumber())
                .append(",1,1]");
    }

    private void appendNewline() {
        result.append("[nl]");
    }

    private void appendLine(Line line) {
        result.append("[jl").append(convertAlignment(line.getTextAlignment()))
                .append("]").append(line.getTextSpecific());
    }

    private int convertAlignment(TextAlignment textAlignment) {
        switch (textAlignment) {
            case LEFT:
                return 2;
            case RIGHT:
                return 4;
            case CENTER:
            default:
                return 3;
        }
    }

    public String getResult() {
        return result.toString();
    }
}
