/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.pchouse;

import java.io.File;
import java.sql.Driver;
import java.sql.DriverManager;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rebelo
 */
public class ClasspathTest {

    public ClasspathTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test
     * @throws java.lang.Exception
     */
    @Test
    public void test() throws Exception {
        System.out.println("add");

        ClassLoader classLoader = getClass().getClassLoader();
        File base = new File(classLoader.getResource("./").getFile());
        File resmysql = new File(base.getAbsolutePath() + "/mysql-connector.jar");
        File dir = Classpath.getAppBaseDirFromThis();
        File lib = new File(dir.getAbsolutePath() + "/lib");
        if (false == lib.exists()) {
            lib.mkdir();
        }

        File mysql = new File(lib.getAbsolutePath() + "/mysql-connector.jar");
        Classpath.addJars(lib);
        if (false == mysql.exists()) {
            java.nio.file.Files.copy(
                    java.nio.file.Paths.get(resmysql.getAbsolutePath()),
                    java.nio.file.Paths.get(mysql.getAbsolutePath())
            );
        }
       
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            while(DriverManager.getDrivers().hasMoreElements()) {
                Driver driver =DriverManager.getDrivers().nextElement();
                System.out.println(driver.getClass().getName());
                if(driver.getClass().getName().equals("com.mysql.cj.jdbc.Driver")){
                    assertTrue(true);
                    return;
                }
            }
            fail("Driver 'com.mysql.cj.jdbc.Driver' not loaded");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            fail(e.getMessage());
        }

    }

}
