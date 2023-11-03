package org.iesvegademijas.stream.test;

import org.iesvegademijas.hibernate.Fabricante;
import org.iesvegademijas.hibernate.FabricanteHome;
import org.iesvegademijas.hibernate.Producto;
import org.iesvegademijas.hibernate.ProductoHome;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static java.util.Comparator.*;


import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;


class TiendaTest {

    @Test
    void testSkeletonFrabricante() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();
            List<Fabricante> listFab = fabHome.findAll();
            //TODO STREAMS
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }


    @Test
    void testSkeletonProducto() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();
            List<Producto> listProd = prodHome.findAll();
            //TODO STREAMS20
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    @Test
    void testAllFabricante() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();
            List<Fabricante> listFab = fabHome.findAll();
            assertEquals(9, listFab.size());
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    @Test
    void testAllProducto() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();
            List<Producto> listProd = prodHome.findAll();
            assertEquals(11, listProd.size());
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }


    }

    /**
     * 1. Lista los nombres y los precios de todos los productos de la tabla producto
     */
    @Test
    void test1() {

        ProductoHome prodHome = new ProductoHome();

        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            //Opcion 1
            List<String> listaNombrePrecio = listProd.stream().map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();
            listaNombrePrecio.forEach(s -> System.out.println(s));

            //Opcion 2
            List<String[]> listaNombrePrecioArrays = listProd.stream()
                    .map(producto -> new String[]{producto.getNombre(), Double.toString(producto.getPrecio())})
                    .toList();
            //Opcion 3
            List<Object[]> listaNombrePrecioArraysObj = listProd.stream()
                    .map(producto -> new Object[]{producto.getNombre(), Double.toString(producto.getPrecio())})
                    .toList();

            // Assert
            Assert.assertEquals(11, listaNombrePrecio.size());    //   el tamaño no coincide con los metodos de JM


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }
    }


    /**
     * 2. Devuelve una lista de Producto completa con el precio de euros convertido a dólares .
     */
    @Test
    void test2() {
        double dolarEuro = 0.9;
        DecimalFormat df = new DecimalFormat("#.###");  // patron decimal para salida de un double
        ProductoHome prodHome = new ProductoHome();

        try {
            prodHome.beginTransaction();
            List<Producto> listProd = prodHome.findAll();

            List<String> lstPrecioDollar = listProd.stream()
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + (df.format(p.getPrecio() * dolarEuro)) + "$").toList();
            lstPrecioDollar.forEach(System.out::println);
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 3. Lista los nombres y los precios de todos los productos, convirtiendo los nombres a mayúscula.
     */
    @Test
    void test3() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            List<String> lstNombres = listProd.stream()
                    .map(p -> "Nombres " + p.getNombre().toUpperCase() + " Precio " + p.getPrecio()).toList();
            lstNombres.forEach(System.out::println);

            Assert.assertEquals(11, lstNombres.size());    //   el tamaño no coincide con los metodos de JM
            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 4. Lista el nombre de todos los fabricantes y a continuación en mayúsculas los dos primeros caracteres del nombre del fabricante.
     */
    @Test
    void test4() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String> lstFab = listFab.stream()
                    .map(f -> "Fabricante " + f.getNombre() + " " + f.getNombre().substring(0, 2).toUpperCase()).toList();
            lstFab.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 5. Lista el código de los fabricantes que tienen productos.
     */
    @Test
    void test5() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            List<Integer> lstFabConProd = listFab.stream()
                    .filter(f -> f.getProductos().size() > 0)
                    .map(f -> f.getCodigo()).toList();
            lstFabConProd.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 6. Lista los nombres de los fabricantes ordenados de forma descendente.
     */
    @Test
    void test6() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String> listFabOrdDesc = listFab.stream()
                    .sorted((o1, o2) -> o2.getNombre().compareTo(o1.getNombre()))
                    .map(f -> "Nombre " + f.getNombre()).toList();

            listFabOrdDesc.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 7. Lista los nombres de los productos ordenados en primer lugar por el nombre de forma ascendente y en segundo lugar por el precio de forma descendente.
     */
    @Test
    void test7() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .sorted(Comparator.comparing(Producto::getNombre).thenComparing(Producto::getPrecio))
                    .map(p -> "nombre " + p.getNombre() + " precio " + p.getPrecio())
                    .toList();
            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 8. Devuelve una lista con los 5 primeros fabricantes.
     */
    @Test
    void test8() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            List<Fabricante> lstFab = listFab.stream()
                    .limit(5)
                    .toList();
            lstFab.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 9.Devuelve una lista con 2 fabricantes a partir del cuarto fabricante. El cuarto fabricante también se debe incluir en la respuesta.
     */
    @Test
    void test9() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            List<Fabricante> lstFab = listFab.stream()
                    .skip(3)
                    .limit(2)
                    .toList();
            lstFab.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 10. Lista el nombre y el precio del producto más barato
     */
    @Test
    void test10() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            List<String> lstPro = listProd.stream()
                    .sorted(Comparator.comparing(Producto::getPrecio))
                    .limit(1)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstPro.forEach(System.out::println);


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 11. Lista el nombre y el precio del producto más caro
     */
    @Test
    void test11() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .sorted(Comparator.comparing(Producto::getPrecio).reversed())
                    .limit(1)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstProd.forEach(System.out::println);

/**       Trabajado con optional
 Optional<String> optStringPro = listProd.stream()
 .sorted(Comparator.comparing(Producto::getPrecio).reversed())
 .findFirst().map(p -> p.getNombre());  // este es un final

 //optPro.ifPresent(producto -> System.out.println(producto.getNombre()))
 **/

            listProd.stream()
                    .max(comparing(producto -> producto.getPrecio()));


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 12. Lista el nombre de todos los productos del fabricante cuyo código de fabricante es igual a 2.
     */
    @Test
    void test12() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(producto -> producto.getFabricante().getCodigo() == 2)
                    .map(producto -> "Nombre " + producto.getNombre() + " Cod Fabricante " + producto.getFabricante().getCodigo())
                    .toList();

            lstProd.forEach(System.out::println);


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 13. Lista el nombre de los productos que tienen un precio menor o igual a 120€.
     */
    @Test
    void test13() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            List<String> lstProd = listProd.stream()
                    .filter(producto -> producto.getPrecio() <= 120)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 14. Lista los productos que tienen un precio mayor o igual a 400€.
     */
    @Test
    void test14() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 400)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 15. Lista todos los productos que tengan un precio entre 80€ y 300€.
     */
    @Test
    void test15() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(producto -> producto.getPrecio() >= 80 && producto.getPrecio() <= 300)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 16. Lista todos los productos que tengan un precio mayor que 200€ y que el código de fabricante sea igual a 6.
     */
    @Test
    void test16() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(producto -> producto.getPrecio() > 200 && producto.getFabricante().getCodigo() == 6)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio() + " Cod Fabricante " + p.getFabricante().getCodigo())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 17. Lista todos los productos donde el código de fabricante sea 1, 3 o 5 utilizando un Set de codigos de fabricantes para filtrar.
     */
    @Test
    void test17() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();
            //Closura   el ide asume implicito que es final
            final Set<Integer> setCodValidos = new HashSet<>();
            setCodValidos.add(1);
            setCodValidos.add(3);
            setCodValidos.add(5);

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    // closura  de variable externa al lambda  (rosita)
                    .filter(p -> setCodValidos.contains(p.getFabricante().getCodigo()))
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio() + " Cod Fabricante " + p.getFabricante().getCodigo())
                    .toList();
            lstProd.forEach(System.out::println);


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 18. Lista el nombre y el precio de los productos en céntimos.
     */
    @Test
    void test18() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();
            final Double cambioCentimos = 100.00;
            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio() * cambioCentimos)
                    .toList();
            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 19. Lista los nombres de los fabricantes cuyo nombre empiece por la letra S
     */
    @Test
    void test19() {


        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<Fabricante> lstFab = listFab.stream()
                    .filter(f -> f.getNombre().charAt(0) == 'S')
                    .toList();
            lstFab.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 20. Devuelve una lista con los productos que contienen la cadena Portátil en el nombre.
     */
    @Test
    void test20() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(p -> p.getNombre().toUpperCase().contains("PORTATIL") || p.getNombre().toUpperCase().contains("PORTATIL"))
                    .map(p -> "Nombre " + p.getNombre())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 21. Devuelve una lista con el nombre de todos los productos que contienen la cadena Monitor en el nombre y tienen un precio inferior a 215 €.
     */
    @Test
    void test21() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<String> lstProd = listProd.stream()
                    .filter(p -> p.getNombre().toUpperCase().contains("MONITOR") && p.getPrecio() < 215)
                    .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                    .toList();

            lstProd.forEach(System.out::println);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 22. Lista el nombre y el precio de todos los productos que tengan un precio mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre (en orden ascendente).
     */
    @Test
    void test22() {


        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String> lstProd = listProd.stream()
                        .filter(p -> p.getPrecio() >= 180)
                        .sorted(Comparator.comparingDouble(Producto::getPrecio).reversed()
                                .thenComparing(Producto::getNombre))
                        .map(p -> "Nombre " + p.getNombre() + " Precio " + p.getPrecio())
                        .collect(Collectors.toList());

                lstProd.forEach(System.out::println);
            } else {
                System.out.println("La lista de productos está vacía.");
            }


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 23. Devuelve una lista con el nombre del producto, precio y nombre de fabricante de todos los productos de la base de datos.
     * Ordene el resultado por el nombre del fabricante, por orden alfabético.
     */
    @Test
    void test23() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String> lstProd = listProd.stream()
                        .sorted((o1, o2) -> (o1.getFabricante().getNombre()).compareTo(o2.getFabricante().getNombre()))
                        .map(p -> "Nombre " + p.getNombre() + "-Precio " + p.getPrecio() + "-Fabricante " + p.getFabricante().getNombre())
                        .collect(Collectors.toList());

                lstProd.forEach(System.out::println);
            } else {
                System.out.println("La lista de productos está vacía.");
            }


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 24. Devuelve el nombre del producto, su precio y el nombre de su fabricante, del producto más caro.
     */
    @Test
    void test24() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String> lstProd = listProd.stream()
                        .sorted((o1, o2) -> (int) (o2.getPrecio() - o1.getPrecio()))
                        .limit(1)
                        .map(p -> "Nombre " + p.getNombre() + "-Precio " + p.getPrecio() + "-Fabricante " + p.getFabricante().getNombre())
                        .collect(Collectors.toList());

                lstProd.forEach(System.out::println);
            } else {
                System.out.println("La lista de productos está vacía.");
            }


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 25. Devuelve una lista de todos los productos del fabricante Crucial que tengan un precio mayor que 200€.
     */
    @Test
    void test25() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String> lstProd = listProd.stream()
                        .filter(p -> p.getFabricante().getNombre().equals("Crucial") && p.getPrecio() > 200)
                        .map(p -> "Nombre " + p.getNombre() + "-Precio " + p.getPrecio() + "-Fabricante " + p.getFabricante().getNombre())
                        .collect(Collectors.toList());

                lstProd.forEach(System.out::println);
            } else {
                System.out.println("La lista de productos está vacía.");
            }

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 26. Devuelve un listado con todos los productos de los fabricantes Asus, Hewlett-Packard y Seagate
     */
    @Test
    void test26() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();
            List<String> lstFab = new ArrayList<>();  // listado con los fabricantes a seleccionar
            lstFab.add("Asus");
            lstFab.add("Hewlett-Packard");
            lstFab.add("Seagate");

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String> lstProd = listProd.stream()
                        .filter(p -> lstFab.contains(p.getFabricante().getNombre()))        // uso de Closure
                        .sorted((o1, o2) -> o1.getFabricante().getCodigo() - o2.getFabricante().getCodigo())
                        .map(p -> "Nombre " + p.getNombre() + "-Precio " + p.getPrecio() + "-Fabricante " + p.getFabricante().getNombre())
                        .collect(Collectors.toList());

                lstProd.forEach(System.out::println);
            } else {
                System.out.println("La lista de productos está vacía.");
            }


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 27. Devuelve un listado con el nombre de producto, precio y nombre de fabricante, de todos los productos que tengan un precio mayor o igual a 180€.
     * Ordene el resultado en primer lugar por el precio (en orden descendente) y en segundo lugar por el nombre.
     * El listado debe mostrarse en formato tabla. Para ello, procesa las longitudes máximas de los diferentes campos a presentar y compensa mediante la inclusión de espacios en blanco.
     * La salida debe quedar tabulada como sigue:
     * <p>
     * Producto                Precio             Fabricante
     * -----------------------------------------------------
     * GeForce GTX 1080 Xtreme|611.5500000000001 |Crucial
     * Portátil Yoga 520      |452.79            |Lenovo
     * Portátil Ideapd 320    |359.64000000000004|Lenovo
     * Monitor 27 LED Full HD |199.25190000000003|Asus
     */
    @Test
    void test27() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            if (!listProd.isEmpty()) {
                List<String[]> lstProd = listProd.stream()
                        .filter(p -> p.getPrecio() >= 180)
                        .sorted(comparing(Producto::getPrecio).reversed()
                                .thenComparing(Producto::getNombre))
                        .map(p -> new String[]{p.getNombre(), String.valueOf(p.getPrecio()), p.getFabricante().getNombre()})
                        .toList();

                int maxLength = 0;

                for (String[] p : lstProd) {
                    maxLength = (p[0].length() > maxLength) ? p[0].length() - 1 : maxLength;
                }

                System.out.print("Nombre" + " ".repeat(maxLength - "nombre".length()));
                System.out.print("Precio" + " ".repeat("Precio".length()));
                System.out.print("Fabricante" + " ".repeat("Fabricante".length()));
                System.out.println();
                System.out.println("-" + "-".repeat((maxLength) + ("Precio".length() * 2) + "Fabricante".length()));

                for (String[] p : lstProd) {
                    System.out.print(p[0] + " ".repeat(maxLength - p[0].length()));
                    System.out.print(p[1] + " ".repeat(("Precio".length() * 2) - p[1].length()));
                    System.out.print(p[2]);
                    System.out.println();
                }

            } else {
                System.out.println("La lista de productos está vacía.");
            }

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 28. Devuelve un listado de los nombres fabricantes que existen en la base de datos, junto con los nombres productos que tiene cada uno de ellos.
     * El listado deberá mostrar también aquellos fabricantes que no tienen productos asociados.
     * SÓLO SE PUEDEN UTILIZAR STREAM, NO PUEDE HABER BUCLES
     * La salida debe queda como sigue:
     * Fabricante: Asus
     * <p>
     * Productos:
     * Monitor 27 LED Full HD
     * Monitor 24 LED Full HD
     * <p>
     * Fabricante: Lenovo
     * <p>
     * Productos:
     * Portátil Ideapd 320
     * Portátil Yoga 520
     * <p>
     * Fabricante: Hewlett-Packard
     * <p>
     * Productos:
     * Impresora HP Deskjet 3720
     * Impresora HP Laserjet Pro M26nw
     * <p>
     * Fabricante: Samsung
     * <p>
     * Productos:
     * Disco SSD 1 TB
     * <p>
     * Fabricante: Seagate
     * <p>
     * Productos:
     * Disco duro SATA3 1TB
     * <p>
     * Fabricante: Crucial
     * <p>
     * Productos:
     * GeForce GTX 1080 Xtreme
     * Memoria RAM DDR4 8GB
     * <p>
     * Fabricante: Gigabyte
     * <p>
     * Productos:
     * GeForce GTX 1050Ti
     * <p>
     * Fabricante: Huawei
     * <p>
     * Productos:
     * <p>
     * <p>
     * Fabricante: Xiaomi
     * <p>
     * Productos:
     */
    @Test
    void test28() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            // Obtener un mapa de fabricantes y sus productos asociados
            Map<Fabricante, List<Producto>> fabricantesConProductos = listFab.stream()
                    .flatMap(fabricante -> fabricante.getProductos().stream()
                            .map(producto -> new HashMap.SimpleEntry<>(fabricante, producto)))
                    .collect(Collectors.groupingBy(Map.Entry::getKey,   /** fabricante  keys **/
                            Collectors.mapping(Map.Entry::getValue /**List<Producto>**/, Collectors.toList())));  // todo s los productos al List

            // Imprimir cada fabricante y sus productos asociados
            fabricantesConProductos.forEach((fabricante, productos) -> {
                System.out.println("Fabricante: " + fabricante.getNombre());
                System.out.println("Productos:");
                productos.forEach(producto -> System.out.println("- " + producto.getNombre()));
                System.out.println();

                //En este código, he agregado la parte que permite obtener un mapa de fabricantes y sus productos asociados utilizando streams.
                // Primero, utilizamos listFab.stream() para obtener un flujo de fabricantes.
                // Luego, usamos flatMap() para "aplanar" el flujo de fabricantes y obtener un nuevo flujo que contiene pares de fabricante-producto.
                // Dentro de flatMap(), usamos fabricante.getProductos().stream() para obtener un flujo de productos asociados a cada fabricante.
                // Luego, mapeamos cada par fabricante-producto a una instancia de HashMap.SimpleEntry para poder agruparlos en el mapa final.
            });

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 29. Devuelve un listado donde sólo aparezcan aquellos fabricantes que no tienen ningún producto asociado.
     */
    @Test
    void test29() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<Fabricante> fabricantesSinProductos = listFab.stream()
                    .filter(fabricante -> fabricante.getProductos().isEmpty())
                    .collect(Collectors.toList());

            fabricantesSinProductos.forEach(System.out::println);


            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 30. Calcula el número total de productos que hay en la tabla productos. Utiliza la api de stream.
     */
    @Test
    void test30() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            int numProd = (int) listProd.stream().count();

            System.out.println("El numero de productos es de: " + numProd);

            Assert.assertEquals(11, numProd);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 31. Calcula el número de fabricantes con productos, utilizando un stream de Productos.
     */
    @Test
    void test31() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            Set<Fabricante> fabricantes = listProd.stream()
                    .filter(producto -> producto.getFabricante().getProductos().size() > 0)
                    .map(Producto::getFabricante)
                    .collect(Collectors.toSet());

            System.out.println("El numero de Fabricantes con productos es de " + fabricantes.size());

            Assert.assertEquals(7, fabricantes.size());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 32. Calcula la media del precio de todos los productos
     */
    @Test
    void test32() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();


            List<Producto> listProd = prodHome.findAll();
            List<Double> precios = listProd.stream()
                    .map(producto -> producto.getPrecio())
                    .collect(Collectors.toList());

            Double media = precios.stream().reduce(0.0, (d1, d2) -> d1 + d2) / precios.size();
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("La media de los precios es de: " + df.format(media));

            Assert.assertEquals("271,72", df.format(media).toString());


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 33. Calcula el precio más barato de todos los productos. No se puede utilizar ordenación de stream.
     */
    @Test
    void test33() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            Optional<Producto> productoMasBarato = Optional.ofNullable(Collections.min(listProd, Comparator.comparing(Producto::getPrecio)));

            if (productoMasBarato.isPresent()) {
                double precioMasBarato = productoMasBarato.get().getPrecio();
                System.out.println("El precio más barato es: " + precioMasBarato);
            } else {
                System.out.println("No se encontraron productos");
            }


            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 34. Calcula la suma de los precios de todos los productos.
     */
    @Test
    void test34() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<Double> precios = listProd.stream()
                    .map(producto -> producto.getPrecio())
                    .collect(Collectors.toList());

            Double sumaPrecios = precios.stream().reduce(0.0, (d1, d2) -> d1 + d2);
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("La suma de los precios es de: " + df.format(sumaPrecios));

            Assert.assertEquals("2988,96", df.format(sumaPrecios).toString());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 35. Calcula el número de productos que tiene el fabricante Asus.
     */
    @Test
    void test35() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();
            List<Producto> lstProd = listProd.stream()
                    .filter(p -> p.getFabricante().getNombre().equals("Asus"))
                    .toList();
            int numprod = lstProd.size();

            System.out.println("El fabricante Asus tiene " + numprod + " Productos");

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 36. Calcula la media del precio de todos los productos del fabricante Asus.
     */
    @Test
    void test36() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            Double media = 0.0;

            List<Producto> listProd = prodHome.findAll();
            List<Double> precios = listProd.stream()
                    .filter(producto -> producto.getFabricante().getNombre().toUpperCase().equals("ASUS"))
                    .map(producto -> producto.getPrecio())
                    .collect(Collectors.toList());

            Double mediaPrecios = precios.stream().reduce(0.0, (d1, d2) -> d1 + d2) / precios.size();
            DecimalFormat df = new DecimalFormat("#.##");

            System.out.println("La media de los precios del fabricante Asus es de: " + df.format(mediaPrecios));

            Assert.assertEquals("224", df.format(mediaPrecios).toString());

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }


    /**
     * 37. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos que tiene el fabricante Crucial.
     * Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
     */
    @Test
    void test37() {

        ProductoHome prodHome = new ProductoHome();
        try {
            prodHome.beginTransaction();

            List<Producto> listProd = prodHome.findAll();

            OptionalDouble max = listProd.stream()
                    .mapToDouble(Producto::getPrecio)
                    .max();
            OptionalDouble min = listProd.stream()
                    .mapToDouble(Producto::getPrecio)
                    .min();
            OptionalDouble media = listProd.stream()
                    .mapToDouble(Producto::getPrecio)
                    .average();
            long total = listProd.stream()
                    .count();

            // Forma dos todo en un solo Stream
            DoubleSummaryStatistics statistics = listProd.stream()
                    .mapToDouble(Producto::getPrecio)
                    .summaryStatistics();

            double max2 = statistics.getMax();
            double min2 = statistics.getMin();
            double media2 = statistics.getAverage();
            long total2 = statistics.getCount();

            System.out.println("Maximo " + max + "\nMinimo " + min + "\nMedia " + media + "\nTotalProductos " + total);

            System.out.println("Maximo " + max2 + "\nMinimo " + min2 + "\nMedia " + media2 + "\nTotalProductos " + total2);

            prodHome.commitTransaction();
        } catch (RuntimeException e) {
            prodHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 38. Muestra el número total de productos que tiene cada uno de los fabricantes.
     * El listado también debe incluir los fabricantes que no tienen ningún producto.
     * El resultado mostrará dos columnas, una con el nombre del fabricante y otra con el número de productos que tiene.
     * Ordene el resultado descendentemente por el número de productos. Utiliza String.format para la alineación de los nombres y las cantidades.
     * La salida debe queda como sigue:
     * <p>
     * Fabricante     #Productos
     * -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
     * Asus              2
     * Lenovo            2
     * Hewlett-Packard   2
     * Samsung           1
     * Seagate           1
     * Crucial           2
     * Gigabyte          1
     * Huawei            0
     * Xiaomi            0
     */
    @Test
    void test38() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            Map<String, Integer> listFabProd = listFab.stream()
                    .collect(Collectors.toMap(
                            fabricante -> fabricante.getNombre(),
                            fabricante -> fabricante.getProductos().size()
                    ));

            Map<String, Integer> listFabOrdenadoPorValor = listFabProd.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new
                    ));

            // Imprimir cada fabricante y sus productos asociados
            int maxLength = 0;

            for (Fabricante p : listFab) {
                maxLength = (p.getNombre().length() > maxLength) ? p.getNombre().length() : maxLength;
            }

            System.out.print("Fabricante" + " ".repeat(maxLength + 2 - "Fabricante".length()));
            System.out.println("Cantidad");
            System.out.println("-".repeat(maxLength + 2 + "Cantidad".length()));

            int finalMaxLength = maxLength;
            listFabOrdenadoPorValor.forEach((fabricante, productos) -> {
                ;
                System.out.print(fabricante + " ".repeat(finalMaxLength + 2 - fabricante.length()));
                System.out.println(productos);
            });


            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 39. Muestra el precio máximo, precio mínimo y precio medio de los productos de cada uno de los fabricantes.
     * El resultado mostrará el nombre del fabricante junto con los datos que se solicitan. Realízalo en 1 solo stream principal. Utiliza reduce con Double[] como "acumulador".
     * Deben aparecer los fabricantes que no tienen productos.
     */
    @Test
    void test39() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            Map<Fabricante, Double[]> mapFabProd = listFab.stream()
                    .collect(Collectors.toMap(
                            fabricante -> fabricante,
                            fabricante -> {
                                Set<Producto> productos = fabricante.getProductos();
                                // Calcular el precio mínimo, máximo y medio
                                double minPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .min()
                                        .orElse(0.0);

                                double maxPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .max()
                                        .orElse(0.0);

                                double mediaPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .average()
                                        .orElse(0.0);

                                // Crear el array con los valores calculados
                                Double[] valores = {minPrecio, maxPrecio, mediaPrecio};

                                return valores;
                            }
                    ));

            mapFabProd.forEach((s, doubles) -> {

                System.out.print(s.getNombre() + " ".repeat(20 - s.getNombre().length()));
                System.out.print("\tPrecio mínimo: " + doubles[0] + " ".repeat(7 - String.valueOf(doubles[0]).length()));
                System.out.print("\tPrecio máximo: " + doubles[1] + " ".repeat(7 - String.valueOf(doubles[1]).length()));
                System.out.print("\tPrecio medio: " + doubles[2]);
                System.out.println();
            });


            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 40. Muestra el precio máximo, precio mínimo, precio medio y el número total de productos de los fabricantes que tienen un precio medio superior a 200€.
     * No es necesario mostrar el nombre del fabricante, con el código del fabricante es suficiente.
     */
    @Test
    void test40() {

        FabricanteHome fabHome = new FabricanteHome();
        try {
            fabHome.beginTransaction();
            List<Fabricante> listFab = fabHome.findAll();
            Map<String, Double[]> mapFabProd = listFab.stream()
                    .collect(Collectors.toMap(
                            fabricante -> fabricante.getNombre(),
                            fabricante -> {
                                Set<Producto> productos = fabricante.getProductos();
                                // Calcular el precio mínimo, máximo y medio
                                double minPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .min()
                                        .orElse(0.0);
                                double maxPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .max()
                                        .orElse(0.0);
                                double mediaPrecio = productos.stream()
                                        .mapToDouble(Producto::getPrecio)
                                        .average()
                                        .orElse(0.0);
                                double numProd = productos.size();

                                // Crear el array con los valores calculados
                                Double[] valores = {minPrecio, maxPrecio, mediaPrecio, numProd};

                                return valores;
                            }
                    ));

            mapFabProd.forEach((s, doubles) -> {
                if (doubles[2] > 200) {
                    System.out.print(s + " ".repeat(10 - s.length()));
                    System.out.print("\tPrecio mínimo: " + doubles[0] + " ".repeat(7 - String.valueOf(doubles[0]).length()));
                    System.out.print("\tPrecio máximo: " + doubles[1] + " ".repeat(7 - String.valueOf(doubles[1]).length()));
                    System.out.print("\tPrecio medio: " + doubles[2] + " ".repeat(7 - String.valueOf(doubles[1]).length()));
                    System.out.print("\tCantidad: " + doubles[3]);
                    System.out.println();
                }
            });
            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 41. Devuelve un listado con los nombres de los fabricantes que tienen 2 o más productos.
     */
    @Test
    void test41() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String> lstFabProd = listFab.stream()
                    .filter(f -> f.getProductos().size() >= 2)
                    .map(fabricante -> fabricante.getNombre())
                    .toList();

            lstFabProd.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }

    /**
     * 42. Devuelve un listado con los nombres de los fabricantes y el número de productos que tiene cada uno con un precio superior o igual a 220 €.
     * Ordenado de mayor a menor número de productos.
     */
    @Test
    void test42() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();

            List<String> resultado = listFab.stream()
                    .filter(f -> f.getProductos().stream()
                            .anyMatch(p -> p.getPrecio() >= 220))
                    .sorted(Comparator.comparing(o -> ((Fabricante) o).getProductos().stream()   // me costo ver el object
                                    .filter(p -> p.getPrecio() >= 220)
                                    .count())
                            .reversed())
                    .map(fabricante -> fabricante.getNombre() + ": " +
                            fabricante.getProductos().stream()
                                    .filter(producto -> producto.getPrecio() >= 220)
                                    .count())
                    .collect(Collectors.toList());

            fabHome.commitTransaction();

            resultado.forEach(System.out::println);

        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 43.Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
     */
    @Test
    void test43() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String> fabricantesConPrecioSuperiorA1000 = listFab.stream()
                    .filter(fabricante -> fabricante.getProductos().stream()
                            .mapToDouble(Producto::getPrecio)
                            .sum() > 1000)
                    .map(Fabricante::getNombre)
                    .toList();

            fabricantesConPrecioSuperiorA1000.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 44. Devuelve un listado con los nombres de los fabricantes donde la suma del precio de todos sus productos es superior a 1000 €
     * Ordenado de menor a mayor por cuantía de precio de los productos.
     */
    @Test
    void test44() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String> fabricantesConPrecioSuperiorA1000Ordenados = listFab.stream()
                    .filter(fabricante -> fabricante.getProductos().stream()
                            .mapToDouble(Producto::getPrecio)
                            .sum() > 1000)
                    .sorted(comparingDouble(fabricante -> fabricante.getProductos().stream()
                            .mapToDouble(Producto::getPrecio)
                            .sum()))
                    .map(Fabricante::getNombre)
                    .toList();

            fabricantesConPrecioSuperiorA1000Ordenados.forEach(System.out::println);

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 45. Devuelve un listado con el nombre del producto más caro que tiene cada fabricante.
     * El resultado debe tener tres columnas: nombre del producto, precio y nombre del fabricante.
     * El resultado tiene que estar ordenado alfabéticamente de menor a mayor por el nombre del fabricante.
     */
    @Test
    void test45() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            List<String[]> resultado = listFab.stream()
                    .flatMap(fabricante -> fabricante.getProductos().stream()
                            .map(producto -> new String[]{producto.getNombre(), String.valueOf(producto.getPrecio()), fabricante.getNombre()}))
                    .collect(Collectors.groupingBy(arr -> arr[2], Collectors.maxBy(Comparator.comparingDouble(arr -> Double.parseDouble(arr[1])))))
                    .values()
                    .stream()
                    .map(Optional::get)
                    .sorted(comparing(arr -> arr[2]))
                    .toList();

            for (String[] fila : resultado) {
                System.out.printf("%-20s %-10s %s%n", fila[0], fila[1], fila[2]);
            }

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }

    }

    /**
     * 46. Devuelve un listado de todos los productos que tienen un precio mayor o igual a la media de todos los productos de su mismo fabricante.
     * Se ordenará por fabricante en orden alfabético ascendente y los productos de cada fabricante tendrán que estar ordenados por precio descendente.
     */
    @Test
    void test46() {

        FabricanteHome fabHome = new FabricanteHome();

        try {
            fabHome.beginTransaction();

            List<Fabricante> listFab = fabHome.findAll();
            Map<String, Double> mediaPreciosPorFabricante = listFab.stream()
                    .collect(Collectors.toMap(Fabricante::getNombre,
                            f -> f.getProductos().stream()
                                    .mapToDouble(Producto::getPrecio)
                                    .average()
                                    .orElse(0.0)));

            // Filtrar los productos que tienen un precio mayor o igual a la media
            List<Producto> productosFiltrados = new ArrayList<>();
            for (Fabricante f : listFab) {
                for (Producto p : f.getProductos()) {
                    if (p.getPrecio() >= mediaPreciosPorFabricante.get(p.getFabricante().getNombre())) {
                        productosFiltrados.add(p);
                    }
                }
            }
            // con bucle porque estas lineas no me funcionaban *****
            //productosFiltrados.sort(comparing(Producto::getFabricante)
            //        .thenComparing(comparingDouble(Producto::getPrecio).reversed()));

            // Imprimir los productos filtrados
            for (Producto producto : productosFiltrados) {
                System.out.println(producto);
            }

            fabHome.commitTransaction();
        } catch (RuntimeException e) {
            fabHome.rollbackTransaction();
            throw e; // or display error message
        }
    }
}

