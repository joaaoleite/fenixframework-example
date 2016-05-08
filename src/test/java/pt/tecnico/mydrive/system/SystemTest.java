package pt.tecnico.mydrive.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.service.AbstractServiceTest ;
import pt.tecnico.mydrive.presentation.*;

public class SystemTest extends AbstractServiceTest {

    private PbShell sh;

    protected void populate() {
        sh = new PbShell();
    }

    @Test
    public void success() {
        // code here
    }
}
