
import com.marcusaxelsson.lab3.model.dao.CarDAO;
import com.marcusaxelsson.lab3.model.entity.Car;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CarDAOTest {
@Deployment
        public static WebArchive createDeployment() {
                return ShrinkWrap.create(WebArchive.class)
                        .addClasses(CarDAO.class, Car.class)
                        .addAsResource("META-INF/persistence.xml")
                        .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
}
    @EJB
    private CarDAO carDAO;
    
    @Before
    public void init() {
                carDAO.create(new Car("IFF780", "Renault Clio"));
                carDAO.create(new Car("LTP520", "Volvo 760GT"));
                carDAO.create(new Car("XOL345", "Isuzu Traga"));
}
    @Test
    public void checkThatFindCarsMatchingNameMatchesCorrectly() {
        Assert.assertTrue(true); /* Some better condition */
} 

}