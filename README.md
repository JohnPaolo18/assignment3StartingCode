Word Tracker Program -- ver 1.0
Submitted by TeamPurah
Submitted to Maryam Moussavi
=====================
This program processes text files and tracks the occurrences of words, including their file locations, line numbers, and frequencies. It outputs the results in the console or saves them to an output file.

Requirements
------------
- Java Development Kit (JDK) 8 or higher
- Text files to process

Installation
------------
1. Download the `WordTracker.jar` file from the provided source.
2. Ensure the `WordTracker.jar` file is placed in a directory where it can access your input text files.

Usage Instructions
------------------
Run the program from the command line using the following syntax:

    java -jar WordTracker.jar <file1> <file2> ... -pf/-pl/-po [-f <output.txt>]

Parameters:
- `<file1>`, `<file2>`: One or more text files to process.
- `-pf`: Prints words in alphabetical order along with the files in which they occur.
- `-pl`: Prints words in alphabetical order along with the files and line numbers where they occur.
- `-po`: Prints words in alphabetical order along with the files, line numbers, and frequency of occurrences.
- `-f <output.txt>`: (Optional) Saves the output to the specified file instead of printing it to the console.

Examples
--------
1. Print results to the console for a single file:
   
       java -jar WordTracker.jar testFile.txt -pf

2. Process multiple files and save the results to `output.txt`:
   
       java -jar WordTracker.jar testFile1.txt testFile2.txt -pl -f output.txt

3. Track words with detailed occurrences and save to `output.txt`:
   
       java -jar WordTracker.jar testFile1.txt testFile2.txt -po -f output.txt

Repository Persistence
----------------------
The program maintains a record of all previously processed files and their words using a binary file (`repository.ser`):
- If `repository.ser` exists, it will be loaded automatically when the program starts.
- Processed results from new files are added to the repository.
- The repository is updated each time the program runs.

Output Format
-------------
- **-pf**: Lists words with the files they occur in.
- **-pl**: Includes line numbers along with the files.
- **-po**: Includes line numbers and the frequency of word occurrences.

Troubleshooting
---------------
- If the program fails to run, ensure:
  - Java is installed and added to your system's PATH.
  - The `WordTracker.jar` file is in the correct location.
  - Input files are accessible and readable.
- If the repository does not restore properly, try deleting `repository.ser` and rerun the program.
