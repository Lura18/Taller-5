package uniandes.dpoo.hamburguesas.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RestauranteTest {

	private Restaurante restaurante;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante();
    }

    @Test
    void testConstructor() {
        assertNotNull(restaurante.getPedidos());
        assertNotNull(restaurante.getIngredientes());
        assertNotNull(restaurante.getMenuBase());
        assertNotNull(restaurante.getMenuCombos());
        assertNull(restaurante.getPedidoEnCurso());
    }

    @Test
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException {
        assertNull(restaurante.getPedidoEnCurso());
        restaurante.iniciarPedido("Juan", "Calle 123");
        assertNotNull(restaurante.getPedidoEnCurso());
        assertEquals("Juan", restaurante.getPedidoEnCurso().getNombreCliente());
    }

    @Test
    void testIniciarPedidoYaEnCurso() {
    	Exception exp = assertThrows(YaHayUnPedidoEnCursoException.class, () -> {
            restaurante.iniciarPedido("Juan", "Calle 123");
            restaurante.iniciarPedido("Pedro", "Calle 456");
        });
    	assertEquals("Ya existe un pedido en curso, para el cliente Juan así que no se puede crear un pedido para Pedro", exp.getMessage());
    }

    @Test
    void testCerrarYGuardarPedidoSinPedidoEnCurso() {
        Exception exp = assertThrows(NoHayPedidoEnCursoException.class, () -> {
            restaurante.cerrarYGuardarPedido();
        });
        assertEquals("Actualmente no hay un pedido en curso", exp.getMessage());
    }

    @Test
    void testCerrarYGuardarPedidoConPedidoEnCurso() throws YaHayUnPedidoEnCursoException, IOException, NoHayPedidoEnCursoException {
        restaurante.iniciarPedido("Juan", "Calle 123");
        assertNotNull(restaurante.getPedidoEnCurso());
        restaurante.cerrarYGuardarPedido();
        assertNull(restaurante.getPedidoEnCurso());
    }

    @Test
    void testCargarIngredientes() throws HamburguesaException, IOException {
        File ingredientesFile = new File("./data/ingredientes.txt");
        File menuFile = new File("./data/menu.txt");
        File combosFile = new File("./data/combos.txt");
        restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);
        assertFalse(restaurante.getIngredientes().isEmpty());
        assertFalse(restaurante.getMenuBase().isEmpty());
        assertFalse(restaurante.getMenuCombos().isEmpty());
    }

    @Test
    void testCargarIngredientesRepetidos() {
    	Exception exp = assertThrows(IngredienteRepetidoException.class, () -> {
        	ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
        	Ingrediente repetido = new Ingrediente("lechuga", 5000);
        	ingredientes.add(repetido);
            File ingredientesFile = new File("./data/ingredientes.txt");
            File menuFile = new File("./data/menu.txt");
            File combosFile = new File("./data/combos.txt");
            restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);
        });
    	assertEquals("El ingrediente lechuga está repetido", exp.getMessage());
    }

    @Test
    void testCargarMenuRepetidos() {
    	Exception exp = assertThrows(ProductoRepetidoException.class, () -> {
        	ArrayList<ProductoMenu> prod = restaurante.getMenuBase();
        	ProductoMenu repetido = new ProductoMenu("corral", 1400);
        	prod.add(repetido);
            File ingredientesFile = new File("./data/ingredientes.txt");
            File menuFile = new File("./data/menu.txt");
            File combosFile = new File("./data/combos.txt");
            restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);
        });
        assertEquals("El producto corral está repetido", exp.getMessage());
    }
    
    @Test
    void testCargarComboRepetidos() {
    	Exception exp = assertThrows(ProductoRepetidoException.class, () -> {
            File ingredientesFile = new File("./data/ingredientes.txt");
            File menuFile = new File("./data/menu.txt");
            File combosFile = new File("./data/combo_repetido.txt");
            restaurante.cargarInformacionRestaurante(ingredientesFile, menuFile, combosFile);
        });
    	assertEquals("El producto combo corral está repetido", exp.getMessage());
    }

    @Test
    void testCargarCombosConProductoFaltante() {
        Exception exp = assertThrows(ProductoFaltanteException.class, () -> {
            File ingredientesFile = new File("./data/ingredientes.txt");
            File menuFileError = new File("./data/menu.txt");
            File combosFile = new File("./data/combo_ing_faltante.txt");
            restaurante.cargarInformacionRestaurante(ingredientesFile, menuFileError, combosFile);
        });
        
        assertEquals("El producto leche no aparece en la información del restaurante", exp.getMessage());
    }
}
