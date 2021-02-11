package f06ModalReader;

import f06ModalReader.model.NXReader;

public class ReaderTest {

	/**
	 * TEST: reads NX & MSC Nastran *.f06 files for test
	 */

	public static void main(final String[] args) {

		try {
			System.out.println("\n");
			System.out.println("Working Directory = " + System.getProperty("user.dir"));

			// SOL103_response.f06
			final NXReader f06file1 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL103_response.f06");
			System.out.println(f06file1.getFile());
			System.out.println(f06file1.getRow(f06file1.getNumberOfModes()-1));

			// SOL103_free_MSC.f06
			final NXReader f06file2 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL103_free_MSC.f06");
			System.out.println(f06file2.getFile());
			System.out.println(f06file2.getRow(f06file2.getNumberOfModes()-1));

			// SOL103_SE.f06
			final NXReader f06file3 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL103_SE.f06");
			System.out.println(f06file3.getFile());
			System.out.println(f06file3.getRow(f06file3.getNumberOfModes()-1));

			// SOL111_MSC.f06
			final NXReader f06file4 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL111_MSC.f06");
			System.out.println(f06file4.getFile());
			System.out.println(f06file4.getRow(f06file4.getNumberOfModes()-1));

			// SOL111_SE.f06
			final NXReader f06file5 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL111_SE.f06");
			System.out.println(f06file5.getFile());
			System.out.println(f06file5.getRow(f06file5.getNumberOfModes()-1));

			// SOL103 multiple of 50
			final NXReader f06file6 = new NXReader(
				"src/test/java/f06ModalReader/test_f06_files/SOL103_multiple_of_50.f06");
			System.out.println(f06file6.getFile());
			System.out.println(f06file6.getRow(f06file6.getNumberOfModes()-1));

		} catch (final NullPointerException e) {
			System.err.println("Null Pointer");
		}
	}
}