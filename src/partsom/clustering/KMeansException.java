/*
 * 2010, http://partSOM4OurGrid.sourceforge.net
 * This file is part of partSOM4OurGrid 
 *
 * partSOM4OurGrid is free software: you can redistribute it and/or modify it under the
 * terms of the Artistic License 2.0 as published by the OSI.
 * 
 * This program is distributed in hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the Artistic License 2.0
 * for more details. 
 * 
 * You should have received a copy of the Artistic License 2.0
 * along with this program. If not, see <http://www.osi.org/licenses/>.
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
