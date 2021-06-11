package com.cgi.charm.dynac.mapping;

import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Transform;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Assert;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;

/**
 * @author CHARM CGI TEAM
 */
public class XsltTest {

	private String xsltFilename;
	private Input.TransformationBuilder transformationBuilder;

	private static final Logger LOGGER = Logger.getLogger(XsltTest.class);
	
	public XsltTest(String xslt) {
		XMLUnit.setXSLTVersion("2.0");
		XMLUnit.setIgnoreWhitespace(true);
		this.xsltFilename = "src/main/resources/" + xslt;
	}

	protected void expectTransformResult(String inputFilename, String expectedResultFilename) {

		try {
			String inputContent = FileUtils.readFileToString(new File("src/test/resources/" + inputFilename));

			File stylesheet = new File(xsltFilename);
			Transform testTransform = new Transform(inputContent, stylesheet);

			configParameters(testTransform);
			testTransform.setOutputProperty("indent", "yes");

			String outputContent = indent(FileUtils.readFileToString(new File("src/test/resources/" + expectedResultFilename)));

			String result = indent(testTransform.getResultString());
			System.out.println("Expected ::  " + outputContent);

			Diff diff = new Diff(outputContent, result);
			if (!diff.identical()) {
				System.out.println("Actual :: " + result);
			}

			assertTrue(diff.toString(), diff.similar());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	protected void configParameters(Transform testTransform) {
		
	}

	/**
	 * Somehow XMLUnit.setIgnoreWhiteSpace is not compatible with saxon, this
	 * function is a workaround for that.
	 * 
	 * @param readFileToString
	 * @return
	 * @throws TransformerException
	 */
	private String indent(String readFileToString) throws TransformerException {
		Transform testTransform2 = new Transform(readFileToString, new File("src/test/resources/messages/identity_transform.xsl"));
		testTransform2.setOutputProperty("indent", "yes");
		return testTransform2.getResultString();
	}


	/**
	 * overloading method using xmlunit 2
	 * add programatically parameters
	 * Example
	 * transformationBuilder.withParameter("organizationId", "RWS");
	 * withParameter(attribute,value)
	 * @param transformationBuilder
	 */
	protected void configParameters(Input.TransformationBuilder transformationBuilder){
		this.transformationBuilder = transformationBuilder;
	}

	/**
	 * Testcase
	 * @param inputFilename
	 * @param expectedResultFilename
	 */
	protected void expectTransformResult2(String inputFilename, String expectedResultFilename) {
		Source inputFile = this.createSourceFileFromStringPath(inputFilename);
		Source expected = this.createSourceFileFromStringPath(expectedResultFilename);

		this.transformationBuilder = Input.byTransforming(inputFile)
				.withStylesheet(Input.fromFile(new File(xsltFilename)));

		configParameters(this.transformationBuilder);

		Source test = transformationBuilder.build();

		org.xmlunit.diff.Diff diff = DiffBuilder.compare(expected)
				.withTest(test)
				.ignoreWhitespace()
				.withNodeFilter(node -> !node.getNodeName().equals("message_id") &&
						!node.getNodeName().equals("message_create_date") &&
						!node.getNodeName().equals("control_timestamp"))
				.build();

		LOGGER.info(diff.toString());
		Assert.assertFalse(diff.toString(), diff.hasDifferences());
	}

	/**
	 * helper method
	 *
	 */
	private Source createSourceFileFromStringPath(String fileName){
		return Input.fromFile(new File("src/test/resources/" + fileName)).build();
	}
}
