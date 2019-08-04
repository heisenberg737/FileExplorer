package filelist;

public class FilesListClass {
    String fileName,numberOfItems;

    public FilesListClass(String fileName, String numberOfItems) {
        this.fileName = fileName;
        this.numberOfItems = numberOfItems;
    }

    public String getFileName() {
        return fileName;
    }

    public String getNumberOfItems() {
        return numberOfItems;
    }
}
