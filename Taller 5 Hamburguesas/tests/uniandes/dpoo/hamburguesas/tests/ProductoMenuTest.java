package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest {

	private ProductoMenu producto;

    @BeforeEach
    void setUp() {
        producto = new ProductoMenu("Hamburguesa Simple", 15000);
    }

    @Test
	@DisplayName("Prueba constructor")
    void testConstructor() {
        assertNotNull(producto, "El producto no debería ser nulo después de la creación.");
        assertEquals("Hamburguesa Simple", producto.getNombre(), "El nombre del producto no es el esperado.");
        assertEquals(15000, producto.getPrecio(), "El precio base del producto no es el esperado.");
    }

    @Test
	@DisplayName("Prueba nombre")
    void testGetNombre() {
        assertEquals("Hamburguesa Simple", producto.getNombre(), "El nombre devuelto por getNombre no es correcto.");
    }

    @Test
	@DisplayName("Prueba precio")
    void testGetPrecio() {
        assertEquals(15000, producto.getPrecio(), "El precio devuelto por getPrecio no es correcto.");
    }

    @Test
	@DisplayName("Prueba factura")
    void testGenerarTextoFactura() {
        String textoFactura = producto.generarTextoFactura();
        String expectedText = "Hamburguesa Simple\n            15000\n";
        assertEquals(expectedText, textoFactura, "El texto generado por generarTextoFactura no es el esperado.");
    }
}
