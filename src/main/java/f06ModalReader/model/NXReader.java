package f06ModalReader.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * Reads f06-file (NX & MSC Nastran) and saves the MODAL EFFECTIVE MASS FRACTION table
 */

public class NXReader {
	
	private final String path;
	private int nmbrOfRoots, rootsFound, count, counter_50;
	private int[] modes;
	private double[] f, X, Y, Z, RX, RY, RZ;

	/**
	 * Constructor
	 * 
	 * @param path - String of file-path
	 */
	public NXReader(final String path) {
		this.path = path;
		readF06File();
	}

	public NXReader(final File file) {
		this.path = file.toString();
		readF06File();
	}

	/**
	 * Read *.f06 NXNastran file - Modal Effective Mass Fraction Table
	 */
	public void readF06File() {

		try (BufferedReader in = Files.newBufferedReader(Paths.get(path), StandardCharsets.ISO_8859_1)) {
			String line = in.readLine(); // read first line
			while (line != null) { // loop complete file
				// CASE CONTROL --> check which MEFFMASS results written in f06 file
				//if (Pattern.compile("MEFFMASS").matcher(line).find() == true ) {
				//	System.out.println(line.split("[\\(\\)]")[1]);
				//}
				// number of evaluated eigenvalues
				if (Pattern.compile("NUMBER\\sOF\\sROOTS\\sFOUND").matcher(line).find() == true 
					&& rootsFound == 0 ) {
					//nmbrOfRoots = Integer.parseInt(line.substring(76, 80).trim());
					String[] tmp = line.split(" "); // split string and use last element
					nmbrOfRoots = Integer.parseInt(tmp[tmp.length-1]);
					rootsFound = 1; // check for first "eigenvalue analysis summary" NOT second!!
				}
				// modal effective mass fractions - FIRST TABLE - translations
				if (Pattern.compile("MODAL\\sEFFECTIVE\\sMASS\\sFRACTION").matcher(line).find() == true 
					&& count == 0) {
					for (int i = 0; i < 5; i++) {
						line = in.readLine();
						// for MSC Nastran (one additional line)
						if( Pattern.compile("DEGREES\\sOF\\sFREEDOM").matcher(line).find() == true )
							line = in.readLine(); 
					} // skip 5 (6 for MSC) lines after header
					// read table of modal effective mass fractions
					modes = new int[nmbrOfRoots]; // allocate vector for all modes
					f = new double[nmbrOfRoots];
					X = new double[nmbrOfRoots];
					Y = new double[nmbrOfRoots];
					Z = new double[nmbrOfRoots];
					counter_50 = 0; // counter for > 50 entries
					for (int j = 0; j < nmbrOfRoots; j++) {
						modes[j] = Integer.parseInt(line.substring(0, 8).trim());
						f[j] = Double.parseDouble(line.substring(12, 24).trim());
						X[j] = Double.parseDouble(line.substring(30, 42).trim());
						Y[j] = Double.parseDouble(line.substring(66, 78).trim());
						Z[j] = Double.parseDouble(line.substring(102, 114).trim());
						line = in.readLine();
						// handle >50-modes 
						counter_50 += 1;
						if( counter_50==50 && j<nmbrOfRoots-1) {
							for (int i = 0; i < 7; i++) {
								line = in.readLine();
							} // skip 7 lines after end of 50-modes block
							counter_50 = 0; // reset counter to 0 for next 50-modes block
						}
					}
					count = 1;
				}
				// modal effective mass fractions - SECOND TABLE - rotation
				if (Pattern.compile("MODAL\\sEFFECTIVE\\sMASS\\sFRACTION").matcher(line).find() == true 
					&& count == 1) {
					for (int i = 0; i < 5; i++) {
						line = in.readLine();
						// for MSC Nastran (one additional line)
						if( Pattern.compile("DEGREES\\sOF\\sFREEDOM").matcher(line).find() == true )
							line = in.readLine(); 
					} // skip 5 (6 for MSC) lines after header
					// read table of modal effective mass fractions
					RX = new double[nmbrOfRoots];
					RY = new double[nmbrOfRoots];
					RZ = new double[nmbrOfRoots];
					counter_50 = 0; // set 50-mode counter to 0
					for (int j = 0; j < nmbrOfRoots; j++) {
						RX[j] = Double.parseDouble(line.substring(30, 42).trim());
						RY[j] = Double.parseDouble(line.substring(66, 78).trim());
						RZ[j] = Double.parseDouble(line.substring(102, 114).trim());
						line = in.readLine();
						// handle >50-modes 
						counter_50 += 1;
						if( counter_50==50 && j<nmbrOfRoots-1) {
							for (int i = 0; i < 7; i++) {
								line = in.readLine();
							} // skip 7 lines after end of 50-modes block
							counter_50 = 0; // reset counter to 0 for next 50-modes block
						}
					}
					count = 1;
				}
				line = in.readLine(); // next line after tables
			}
			in.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter
	 */
	public String getFile() {
		return path;
	}

	public int getNumberOfModes() {
		return nmbrOfRoots;
	}

	public int[] getModes() {
		return modes;
	}

	public double[] getF() {
		return f;
	}

	public double[] getX() {
		return X;
	}

	public double[] getY() {
		return Y;
	}

	public double[] getZ() {
		return Z;
	}

	public double[] getRX() {
		return RX;
	}

	public double[] getRY() {
		return RY;
	}

	public double[] getRZ() {
		return RZ;
	}

	public String getRow(final int rowIndex) {
		return Integer.toString(modes[rowIndex]) + '\t' + Double.toString(f[rowIndex]) + 
				'\t' + Double.toString(X[rowIndex]) + '\t' + Double.toString(Y[rowIndex]) + '\t' + Double.toString(Z[rowIndex]) +
				'\t' + Double.toString(RX[rowIndex]) + '\t' + Double.toString(RY[rowIndex]) + '\t' + Double.toString(RZ[rowIndex]) +
				'\n';
	}
}
