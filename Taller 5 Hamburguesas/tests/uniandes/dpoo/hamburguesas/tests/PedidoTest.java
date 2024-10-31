package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileNotFoundException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {

	private Pedido pedido;
    private ProductoMenu producto1;
    private ProductoMenu producto2;

    @BeforeEach
    void setUp() {
        pedido = new Pedido("Juan Pérez", "Calle 123");
        producto1 = new ProductoMenu("Hamburguesa", 15000);
        producto2 = new ProductoMenu("Papas", 5000);
    }

    @Test
    void testConstructor() {
        assertEquals("Juan Pérez", pedido.getNombreCliente(), "El nombre del cliente no es el esperado.");
        assertNotNull(pedido.getProductos(), "La lista de productos no debería ser nula.");
        assertEquals(0, pedido.getProductos().size(), "La lista de productos debería estar vacía al iniciar.");
    }
    
    @Test
    void testGetIdPedido() {
        int idPedido = pedido.getIdPedido();
        assertTrue(idPedido >= 0, "El ID no puede ser negativo.");
    }

    @Test
    void testGetNombreCliente() {
        assertEquals("Juan Pérez", pedido.getNombreCliente(), "El nombre del cliente no es el esperado.");
    }

    @Test
    void testAgregarProducto() {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        assertEquals(2, pedido.getProductos().size(), "La cantidad de productos en el pedido no es la esperada.");
    }

    @Test
    void testGetPrecioTotalPedido() {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        int precioTotal = pedido.getPrecioTotalPedido();
        assertEquals(23800, precioTotal, "El precio total del pedido no es el esperado.");
    }

    @Test
    void testGenerarTextoFactura() {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        String expectedFactura = "Cliente: Juan Pérez\n" +
                                 "Dirección: Calle 123\n" +
                                 "----------------\n" +
                                 "Hamburguesa\n            15000\n" +
                                 "Papas\n            5000\n" +
                                 "----------------\n" +
                                 "Precio Neto:  20000\n" +
                                 "IVA:          3800\n" +
                                 "Precio Total: 23800\n";

        assertEquals(expectedFactura, pedido.generarTextoFactura(), "La factura generada no es la esperada.");
    }

    @Test
    void testGuardarFactura() throws FileNotFoundException {
        pedido.agregarProducto(producto1);
        pedido.agregarProducto(producto2);
        File archivo = new File("factura.txt");

        try {
            pedido.guardarFactura(archivo);
            assertTrue(archivo.exists(), "El archivo de la factura debería existir.");
        } finally {
            archivo.delete();
        }
    }
    
}
