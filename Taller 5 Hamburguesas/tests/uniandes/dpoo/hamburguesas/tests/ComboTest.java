package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {

	private Combo comboCorral;

    @BeforeEach
    void setUp() {
        ProductoMenu corral = new ProductoMenu("corral", 20000);
        ProductoMenu papasMedianas = new ProductoMenu("papas medianas", 5000);
        ProductoMenu gaseosa = new ProductoMenu("gaseosa", 3000);

        ArrayList<ProductoMenu> itemsCorral = new ArrayList<>();
        itemsCorral.add(corral);
        itemsCorral.add(papasMedianas);
        itemsCorral.add(gaseosa);
        comboCorral = new Combo("combo corral", 0.1, itemsCorral);
    }

    @Test
    void testGetNombre() {
        assertEquals("combo corral", comboCorral.getNombre(), "El nombre del combo no es el esperado.");
    }

    @Test
    void testGetPrecio() {
        assertEquals(25200, comboCorral.getPrecio(), "El precio del combo corral con descuento no es el esperado.");

    }

    @Test
    void testGenerarTextoFactura() {
        String expectedFacturaCorral = "Combo combo corral\n Descuento: 0.1\n            25200\n";
        assertEquals(expectedFacturaCorral, comboCorral.generarTextoFactura(), "La factura del combo corral no es la esperada.");

    }
}
