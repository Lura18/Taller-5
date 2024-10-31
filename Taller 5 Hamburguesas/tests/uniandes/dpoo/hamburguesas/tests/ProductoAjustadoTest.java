package uniandes.dpoo.hamburguesas.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {

	private ProductoMenu productoBase;
    private ProductoAjustado productoAjustado;
    private Ingrediente lechuga;
    private Ingrediente tomate;

    @BeforeEach
    void setUp() {
        productoBase = new ProductoMenu("Hamburguesa", 10000);
        productoAjustado = new ProductoAjustado(productoBase);
        
        tomate = new Ingrediente("tomate", 1000);
        lechuga = new Ingrediente("lechuga", 1000);
    }

    @Test
    void testConstructor() {
        assertNotNull(productoAjustado, "El producto ajustado no debería ser nulo después de la creación.");
        assertEquals("Hamburguesa", productoAjustado.getNombre(), "El nombre del producto ajustado no es el esperado.");
        assertEquals(10000, productoAjustado.getPrecio(), "El precio base del producto ajustado no es el esperado.");
    }
    
    @Test
    public void testGetNombre() {
        assertEquals("Hamburguesa", productoAjustado.getNombre());
    }

    @Test
    void testAgregarIngrediente() {
    	productoAjustado.agregarIngrediente(tomate);
    	productoAjustado.agregarIngrediente(lechuga);
    	assertTrue(productoAjustado.getAgregados().contains(tomate));
    }
    
    @Test
    void testEliminarIngrediente() {
        productoAjustado.eliminarIngrediente(lechuga);  
        assertTrue(productoAjustado.getEliminados().contains(lechuga));
    }

    @Test
    void testGenerarTextoFacturaSinAjustes() {
        String factura = productoAjustado.generarTextoFactura();
        String expectedText = "Hamburguesa            10000\n";
        assertEquals(expectedText, factura, "El texto de la factura para el producto base no es el esperado.");
    }
    
    @Test
    void testGenerarTextoFacturaConAjustes() {
    	productoAjustado.agregarIngrediente(tomate);
    	productoAjustado.eliminarIngrediente(lechuga);
        String factura = productoAjustado.generarTextoFactura();
        String expectedText = "Hamburguesa    +tomate                1000    -lechuga            11000\n";
        assertEquals(expectedText, factura, "El texto de la factura para el producto base no es el esperado.");
    }

    @Test
    public void testGetPrecioConAgregados() {
        productoAjustado.agregarIngrediente(tomate);
        productoAjustado.agregarIngrediente(lechuga);
        
        int precioEsperado = 10000 + tomate.getCostoAdicional() + lechuga.getCostoAdicional();
        assertEquals(precioEsperado, productoAjustado.getPrecio());
    }
}
