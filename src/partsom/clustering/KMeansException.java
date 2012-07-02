/*
 * 2010, http://github.com/pauloewerton/partSOM4Grid
 * This file is part of partSOM4Grid 
 *
 * partSOM4Grid is free software: you can redistribute it and/or modify it under the
 * terms of the Artistic License 2.0 as published by the OSI.
 * 
 * This program is distributed in hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the Artistic License 2.0
 * for more details. 
 * 
 * You should have received a copy of the Artistic License 2.0
 * along with this program. See <www.opensource.org/licenses/artistic-license-2.0>.
 * 
 */

package partsom.clustering;

public class KMeansException extends Exception {

	private static final long serialVersionUID = 1L;

	public KMeansException() {
		
	}

	public KMeansException(String arg0) {
		super(arg0);
	}

	public KMeansException(Throwable arg0) {
		super(arg0);
	}

	public KMeansException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
