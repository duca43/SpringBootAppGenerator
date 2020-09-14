package org.asdm.springbootgeneratorplugin.analyzer;


/**
 * AnalyzeException - special kind of exception that can be thrown by ModelAnalyzer
 */
public class AnalyzeException extends Exception {

	private static final long serialVersionUID = -8260521800600159211L;

	public AnalyzeException(String msg) {
        super(msg);
    }
}
