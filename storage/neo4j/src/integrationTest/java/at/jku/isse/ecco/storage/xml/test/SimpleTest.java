package at.jku.isse.ecco.storage.xml.test;

import at.jku.isse.ecco.EccoService;
import at.jku.isse.ecco.storage.mem.dao.MemEntityFactory;
import at.jku.isse.ecco.storage.neo4j.impl.NeoRepository;
import at.jku.isse.ecco.storage.neo4j.impl.NeoRepositoryDao;
import at.jku.isse.ecco.storage.neo4j.impl.NeoTransactionStrategy;
import org.junit.*;
import org.junit.rules.TemporaryFolder;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.collect.Sets.powerSet;

public class SimpleTest {

    private static final Path repoDir = Paths.get("src\\integrationTest\\data\\.ecco\\");
    private static final String testDataDir = "..\\..\\examples\\demo_variants\\V";

    @BeforeTest(alwaysRun = true)
    public void prepare() throws IOException {
        Files.deleteIfExists(this.repoDir.resolve(".adapters"));
        Files.deleteIfExists(this.repoDir.resolve(".ignores"));
        Files.deleteIfExists(this.repoDir.resolve("ecco.db.xml.zip"));
        Files.createDirectories(repoDir);
    }

    @AfterTest(alwaysRun = true)
    public void shutdown() {
    }


    @Test
    public void fillRepo() {
        // create new repository
        EccoService service = new EccoService();
        service.setRepositoryDir(repoDir);
        service.init();
        System.out.println("Repository initialized.");

        //TODO: iterate directoy
        //TODO: delete after creation
        // commit all existing variants to the new repository
       for (int i = 1; i< 10; i++) {
            service.setBaseDir(Paths.get(testDataDir + i +"\\"));
            service.commit();
            System.out.println("Committed: " + "V" +i);
        }

        // close repository
        service.close();
        System.out.println("Repository closed.");

    }


}
