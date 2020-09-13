package org.asdm.springbootgeneratorplugin.analyzer;

import java.lang.Exception;

/** AnalyzeException - special kind of exception that can be thrown by ModelAnalyzer */
@SuppressWarnings("serial")
public class AnalyzeException extends Exception {

	public AnalyzeException(String msg) {
		super(msg);
	}
}
