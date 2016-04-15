package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ExceptionsTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User luis = mydrive.createUser("luis", "luisinho","luisinho","rwxd----");
        User ze = mydrive.createUser("ze", "zezinho","zezinho","rwxd----");
        
        Dir rootdir = mydrive.getRootDir();
        
        rootdir.getDir("home").getDir("luisinho").createLink(luis,"link","/home/zezinho");
        rootdir.getDir("home").getDir("luisinho").createApp(luis,"app");
        rootdir.getDir("home").getDir("luisinho").createDir(luis,"NewDir");
        rootdir.getDir("home").getDir("luisinho").createPlainFile(luis,"new.txt"); 
   }

	@Test
	public void testFileAlreadyExistsExceptionMessage() {
        final long token = login("luisinho", "luisinho");
        final String filename = "new.txt";
	    
        try {
        	CreateFileService service = new CreateFileService(token, filename, "Plain");
        	service.execute();

	        fail("Expected an FileAlreadyExistsException to be thrown");
	    } catch (FileAlreadyExistsException e) {
	        assertEquals("FileAlreadyExistsException error", "The File " + filename + " already exists.",e.getMessage());
	    }
	}

	@Test
	public void testFileDoesNotExistExceptionMessage() {
        final long token = login("luisinho", "luisinho");
        final String filename = "manel.txt";

	    try {
        	DeleteFileService service = new DeleteFileService(token, filename);
        	service.execute();

	        fail("Expected an FileDoesNotExistException to be thrown");
	    } catch (FileDoesNotExistException e) {
	        assertEquals("FileDoesNotExistException error", "The File " + filename + " does not exist.",e.getMessage());
	    }
	}

	@Test
	public void testFileIsADirExceptionMessage() {
	    final long token = login("luisinho", "luisinho");
        final String filename = "NewDir";

        try {
        	ReadFileService service = new ReadFileService(token, filename);
        	service.execute();

	        fail("Expected an FileIsADirException to be thrown");
	    } catch (FileIsADirException e) {
	        assertEquals("FileIsADirException error", "The File " + filename + " is a Directory.",e.getMessage());
	    }
	}

	@Test
	public void testFileIsAPlainFileExceptionMessage() {
	    final long token = login("luisinho", "luisinho");
        final String path = "/home/luisinho/new.txt";
        final String name = "new.txt";

        try {
        	ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        	service.execute();

        	fail("Expected an FileIsAPlainFileException to be thrown");
        } catch (FileIsAPlainFileException e) {
        	assertEquals("FileIsAPlainFileException error", "The File " + name + " is a PlainFile.",e.getMessage());
        }
    }

    @Test
	public void testFilenameInvalidExceptionMessage() {
	    final long token = login("luisinho", "luisinho");
        final String content = "teste123";
        final String name = "/file/";

        try {
        	CreateFileService service = new CreateFileService(token, name, "Plain", content);
        	service.execute();

        	fail("Expected an FilenameInvalidException to be thrown");
        } catch (FilenameInvalidException e) {
        	assertEquals("FilenameInvalidException error", "The name of the file'" + name + "' is invalid.",e.getMessage());
        }
    }

    @Test
	public void testImportDocExceptionMessage() {
	    try {
	    	ImportMyDriveService service = new ImportMyDriveService(null);
            service.execute();

	    	fail("Expected an ImportDocException to be thrown");
	    } catch (ImportDocException e) {
	    	assertEquals("ImportDocException error", "Error in importing mydrive from XML",e.getMessage());
	    }
	}

	@Test
	public void testInsufficientPermissionsExceptionMessage() {
	    final long token = login("luisinho", "luisinho");
        final String path = "/home/zezinho";

        try {
        	ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        	service.execute();

        	fail("Expected an InsufficientPermissionsException to be thrown");
        } catch(InsufficientPermissionsException e){
        	assertEquals("InsufficientPermissionsException error", "You dont have permission to access zezinho.",e.getMessage());
        }
    }

    @Test
	public void testInvalidFileTypeExceptionMessage() {
	    final long token = login("luisinho", "luisinho");

        try {
        	CreateFileService service = new CreateFileService(token, "teste", "invalidType","");
        	service.execute();

        	fail("Expected an InvalidFileTypeException to be thrown");
        } catch(InvalidFileTypeException e){
        	assertEquals("InvalidFileTypeException error", "The File Type invalidType is invalid.",e.getMessage());
        }
    }

    @Test
	public void testLoginFailedExceptionMessage() {
	    

        try {
            final long token = login("luisinho", "password");
	    	fail("Expected an LoginFailedException to be thrown");
        } catch(LoginFailedException e){
        	assertEquals("LoginFailedException error", "Username/Password is invalid.",e.getMessage());
        }
    }

    @Test
	public void testLinkCantBeEmptyExceptionMessage() {
	    final long token = login("luisinho", "luisinho");

        try {
        	CreateFileService service = new CreateFileService(token, "teste", "Link","");
        	service.execute();

        	fail("Expected an LinkCantBeEmptyException to be thrown");
        } catch(LinkCantBeEmptyException e){
        	assertEquals("LinkCantBeEmptyException error", "The Link teste cant be empty.",e.getMessage());
        }
    }

    @Test
	public void testPathTooLongExceptionMessage() {
	    final long token = login("luisinho", "luisinho");
        final String name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla ut tempor elit. Nullam viverra pharetra ligula, ac aliquam turpis consectetur a. Interdum et malesuada fames ac ante ipsum primis in faucibus. Integer posuere mi in accumsan rhoncus. Donec bibendum nibh ac cursus gravida. Aliquam sollicitudin vulputate tempus. Phasellus in justo efficitur, fermentum arcu sed, fringilla tellus. Pellentesque pretium lorem sit amet mollis scelerisque. Donec eget quam nec nulla volutpat aliquet at eget nulla. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec faucibus viverra magna sit amet commodo. Duis egestas aliquam congue. Morbi euismod interdum nisi, in cursus est feugiat in. Morbi condimentum libero nibh, et elementum orci tempor sed. Aenean vel tellus erat. Morbi sollicitudin tellus eu finibus tempus. Suspendisse scelerisque finibus tristique. Curabitur pretium, arcu nec ullamcorper volutpat, felis lacus iaculis velit, quis tincidunt tellus ipsum vitae libero. Pellentesque tempor scelerisque neque eu porttitor.";

        try {
	    	CreateFileService service = new CreateFileService(token, name, "Plain","");
            service.execute();

	    	fail("Expected an PathTooLongException to be thrown");
        } catch(PathTooLongException e){
        	assertEquals("PathTooLongException error", "The path of the file to be created with name'" + name + "' is going to be too long.",e.getMessage());
        }
    }

    @Test
	public void testTokenDoesNotExistExceptionMessage() {
	    final long token = 123;

        try {
	    	CreateFileService service = new CreateFileService(token, "name", "Plain","");
            service.execute();

	    	fail("Expected an TokenDoesNotExistException to be thrown");
        } catch(TokenDoesNotExistException e){
        	assertEquals("TokenDoesNotExistException error", "The token " + token + " does not exist.",e.getMessage());
        }
    }
}