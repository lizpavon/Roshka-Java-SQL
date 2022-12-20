import java.sql.*;

public class App {
   public static void main(String args[]) throws SQLException {
      //Llamar a la función que desea mostrar:
   }

   //Establecer la conexion con la base de datos
   public static Connection conection (){
      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
                 .getConnection("jdbc:postgresql://localhost:5432/bootcamp_market",
                         "postgres", "987Soltar");
         return c;
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         return null;
         //System.exit(0);
      }
      //System.out.println("Opened database successfully");
   }

   // 1. TOP CLIENTES CON MAS FACTURAS
   public static void clientesConMasFacturas() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio1 = "select c.id, c.nombre, c.apellido, c.nro_cedula, count(f.cliente_id) from factura f join cliente c on c.id = f.cliente_id\n" +
              "group by c.id, c.nombre, c.apellido, c.nro_cedula\n" +
              "order by count(f.cliente_id) desc";
      ResultSet rs = stmt.executeQuery(ejercicio1);
      while(rs.next()){
         //Display values
         System.out.print("ID: " + rs.getInt("id"));
         System.out.print(", Nombre: " + rs.getString("nombre"));
         System.out.print(", Apellido: " + rs.getString("apellido"));
         System.out.print(", NroCedula: " + rs.getString("nro_cedula"));
         System.out.print(", Cantidad: " + rs.getInt("count"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 2. TOP CLIENTES QUE MAS GASTARON
   public static void clientesConMasGastos() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio2 = "select c.nombre as cliente, SUM(round(fd.cantidad*p.precio)) as gasto\n" +
              "from factura_detalle fd join producto p on fd.producto_id = p.id join factura f on f.id = fd.factura_id join cliente c\n" +
              "on f.cliente_id = c.id\n" +
              "group by c.nombre\n" +
              "order by gasto desc";
      ResultSet rs = stmt.executeQuery(ejercicio2);
      while(rs.next()){
         //Display values
         System.out.print("Cliente: " + rs.getString("cliente"));
         System.out.print(", Gasto: " + rs.getDouble("gasto"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 3. TOP MONEDAS MAS UTILIZADAS
   public static void monedasMasUtilizadas() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio3 = "select m.nombre, count(f.moneda_id) from factura f join moneda m on f.moneda_id = m.id\n" +
              "group by m.nombre\n" +
              "order by count(f.moneda_id) desc";
      ResultSet rs = stmt.executeQuery(ejercicio3);
      while(rs.next()){
         //Display values
         System.out.print("Moneda: " + rs.getString("nombre"));
         System.out.print(", Cantidad de veces utilizada: " + rs.getDouble("count"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 4. TOP PROVEEDOR DE PRODUCTOS
   public static void proveedorProductos() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio4 = "select proveedor.nombre, COUNT(producto.id) cantidad FROM proveedor JOIN producto ON producto.proveedor_id = proveedor.id\n" +
              "GROUP BY (proveedor.nombre)\n" +
              "ORDER BY (cantidad) DESC;";
      ResultSet rs = stmt.executeQuery(ejercicio4);
      while(rs.next()){
         //Display values
         System.out.print("Proveedor: " + rs.getString("nombre"));
         System.out.print(", Cantidad de productos: " + rs.getInt("cantidad"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 5. PRODUCTOS MAS VENDIDOS
   public static void productosMasVendidos() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio5 = "select p.nombre, sum(round(fd.cantidad)) from producto p join factura_detalle fd on p.id = fd.producto_id \n" +
              "GROUP BY p.nombre\n" +
              "ORDER BY sum(fd.cantidad) desc";
      ResultSet rs = stmt.executeQuery(ejercicio5);
      while(rs.next()){
         //Display values
         System.out.print("Producto: " + rs.getString("nombre"));
         System.out.print(", Cantidad de producto más vendido: " + rs.getInt("sum"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 6. PRODUCTOS MENOS VENDIDOS
   public static void productosMenosVendidos() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio6 = "select p.nombre, sum(round(fd.cantidad)) from producto p join factura_detalle fd on p.id = fd.producto_id \n" +
              "GROUP BY p.nombre\n" +
              "ORDER BY sum(fd.cantidad)";
      ResultSet rs = stmt.executeQuery(ejercicio6);
      while(rs.next()){
         //Display values
         System.out.print("Producto: " + rs.getString("nombre"));
         System.out.print(", Cantidad de producto menos vendido: " + rs.getInt("sum"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

   // 7. MOSTRAR CIERTOS DATOS
   public static void mostrarDatos() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio7 = "select factura.id ,factura.fecha_emision, cliente.nombre as nombre, cliente.apellido, producto.nombre as producto, factura_detalle.cantidad, factura_tipo.nombre as forma_pago\n" +
              "from factura join cliente on factura.cliente_id = cliente.id join factura_detalle on factura_detalle.factura_id = factura.id\n" +
              "join producto on factura_detalle.producto_id = producto.id join factura_tipo on factura.factura_tipo_id = factura_tipo.id\n" +
              "order by factura.id desc";
      ResultSet rs = stmt.executeQuery(ejercicio7);
      while(rs.next()){
         //Display values
         System.out.print("Producto: " + rs.getInt("id"));
         System.out.print(", Fecha de emision: " + rs.getDate("fecha_emision"));
         System.out.print(", Nombre del Cliente: " + rs.getString("nombre"));
         System.out.print(", Apellido del Cliente: " + rs.getString("apellido"));
         System.out.print(", Nombre del producto: " + rs.getString("producto"));
         System.out.print(", Cantidad comprada: " + rs.getInt("cantidad"));
         System.out.print(", Forma de Pago: " + rs.getString("forma_pago"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }


   // 8. MONTOS DE FACTURAS ORDENADAS SEGUN TOTALES
   public static void facturasOrdenadas() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio8 = "select factura.id, sum(round(factura_detalle.cantidad*producto.precio)) as Monto from factura_detalle join producto on factura_detalle.producto_id = producto.id\n" +
              "join factura on factura.id = factura_detalle.factura_id\n" +
              "group by factura.id\n" +
              "order by sum(round(factura_detalle.cantidad*producto.precio)) desc";
      ResultSet rs = stmt.executeQuery(ejercicio8);
      while(rs.next()){
         //Display values
         System.out.print("ID de la factura: " + rs.getInt("id"));
         System.out.print(", Monto de la factura " + rs.getInt("monto"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

// 9. MOSTRAR EL IVA
   public static void mostrarIVA() throws SQLException {
      Connection c = conection();
      Statement stmt = c.createStatement();
      String ejercicio9 = "select factura.id as NroFactura, round(sum((factura_detalle.cantidad*producto.precio)*0.11)) as MontoIva from factura_detalle join producto on factura_detalle.producto_id = producto.id\n" +
              "join factura on factura.id = factura_detalle.factura_id\n" +
              "group by factura.id\n" +
              "order by round(sum((factura_detalle.cantidad*producto.precio)*0.11))";
      ResultSet rs = stmt.executeQuery(ejercicio9);
      while(rs.next()){
         //Display values
         System.out.print("ID de la factura: " + rs.getInt("nrofactura"));
         System.out.print(", Monto del IVA " + rs.getInt("montoiva"));
         System.out.println("\n");
      }
      stmt.close();
      rs.close();
   }

}