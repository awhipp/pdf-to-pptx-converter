# Batch PDF Merging Tool

This tool allows users to batch merge N PDF files into one. Not only sequentially but, you can choose which order the pages are merged in across all of the PDFs.

## How to use

* application.properties - this file holds the properties number.of.files and result.name
* order.txt - describes the page order to merge in based on the number of files
* sites.txt - this file holds a new line delimited list of identifiers that reference the like files across all the folders

## application.properties description

Holds a new line delimited list of orderings in the following pattern a:b-c-i

* a is the folder number that is being referenced for the current identifier (from sites.txt)
* b is starting page
* c is the ending page
* i is option and if included means to not list a page number on this page

For instance: 5:2-21 means that

* Find the PDF with the current identifier in folder 5
* Merge into the final PDF pages 2 to 21

## Folder Structure

Set up folders based on the number.of.files property. For example, if this property is 5, then set up 5 folders named file1/, file2/, file3/, file4/, and file5/ in the root directory.

## Example Execution

* sites.txt has two items: test1 and test2
* application.properties states number.of.files is 2 and result.name is the prefix Final-
* we would create two folders file1/ and file2/ and place two PDFs in file1/ and two PDFs in file2/
* One PDF in file1/ has the identifier test1 in the name, and one PDF in file1/ has the identifier test2 in the name.
* order.txt has three lines. the first is 1:1-5-i, the second is 2:1-7, and the third is 1:6-10
* This will take pages 1 to 5 of the first file, 1 to 7 of the second, and then 6-10 of the first file and merge them together in that order.
* Page 1 will not have numbering and page 2 will be numbered with a 2 in the bottom left. 
* This will happen for both test1 files and then the test2 files.
