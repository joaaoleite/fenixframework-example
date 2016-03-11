package pt.tecnico.mydrive.exception;




public abstract class MyDriveException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MyDriveException() {
    	super();
    }

    public MyDriveException(String msg) {
        super(msg);
    }
}
