package br.com.vendas.util.file;

/**
 * Created by ladairsmiderle on 05/11/2015.
 */

import br.com.vendas.core.util.DateUtil;
import br.com.vendas.domain.customer.Customer;
import br.com.vendas.domain.order.Installment;
import br.com.vendas.domain.order.Order;
import br.com.vendas.domain.order.OrderItem;
import br.com.vendas.domain.order.OrderPayment;
import br.com.vendas.domain.product.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.*;

public class GeraPdf {

    private Order order;

    private static Font labelTituloFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    private static Font labelFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);

    public static void main(String[] args) {

        try {

            Order order = new Order();
            Customer customer = new Customer();
            customer.setName("Ladair Carlos Smiderle Junior");
            customer.setCellPhone("99263778");
            customer.setStreet("Rua itacolomi");

            OrderItem orderItem1 = new OrderItem();
            Product product1 = new Product();
            product1.setDescription("Algum produto qualquer");
            product1.setSalePrice(13.45);

            orderItem1.setProduct(product1);
            orderItem1.setSalePrice(29.54);
            orderItem1.setQuantity(10.0);

            OrderItem orderItem2 = new OrderItem();

            Product product = new Product();
            product.setDescription("Algum produto qualquer");
            product.setSalePrice(13.45);

            orderItem2.setProduct(product);
            orderItem2.setSalePrice(29.54);
            orderItem2.setQuantity(2.0);

            order.setOrdersItens(new HashSet<OrderItem>(Arrays.asList(orderItem1, orderItem2)));
            order.setCustomer(customer);
            order.setObservation("Alguma observação");
            order.setNetValue(12.54);
            order.setIssuanceTime(new Date());

            Installment installment = new Installment();
            installment.setDescription("20 30 60 90");

            order.setInstallment(installment);


            OrderPayment orderPayment = new OrderPayment();
            orderPayment.setExpirationDate(new Date());
            orderPayment.setInstallmentValue(10.0);

            OrderPayment orderPayment1 = new OrderPayment();
            orderPayment1.setExpirationDate(new Date());
            orderPayment1.setInstallmentValue(10.9849849840);

            order.setOrdersPayments(new HashSet<>(Arrays.asList(orderPayment, orderPayment1)));


            FileOutputStream fileOutputStream = new FileOutputStream("c:\\Temp\\MeuPdf.pdf");

            GeraPdf geraPdf = new GeraPdf(order);
            geraPdf.writePdf(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public GeraPdf(Order order) {
        this.order = order;
    }

    public void writePdf(OutputStream os) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, os);
            document.open();
            addMetaData(document);
            //addTitlePage( document );
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Pedido VendasUp");
        document.addSubject("Pedido VendasUp");
        document.addKeywords("Pedido,Venda, VendasUp");
        document.addAuthor("VendasUp");
        document.addCreator("VendasUp");
    }

    private void addContent(Document document) throws DocumentException {

        geraGridTopo(document);
        geraGridProdutos(document);

    }

    private Image createImage() {
        Image image = null;
        try {
            URL resource = getClass().getClassLoader().getResource("image/vendasup-horizontal.jpg");
            image = Image.getInstance(resource);
            //image.scaleAbsolute(100, 100);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void geraGridProdutos(Document document) throws DocumentException {


        Set<OrderItem> ordersItens = new TreeSet<>(order.getOrdersItens());

        PdfPTable table = new PdfPTable(5);
        table.setWidths(new float[]{5, 58, 7, 15, 15});

        PdfPCell th = new PdfPCell(new Phrase("", labelTituloFont));
        th.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(th);

        th = new PdfPCell(new Phrase("Produto", labelTituloFont));
        th.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(th);

        th = new PdfPCell(new Phrase("Qtd", labelTituloFont));
        th.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(th);

        th = new PdfPCell(new Phrase("Valor", labelTituloFont));
        th.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(th);

        th = new PdfPCell(new Phrase("Total", labelTituloFont));
        th.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(th);


        table.setHeaderRows(1);
        table.setWidthPercentage(100);



        for (OrderItem orderIten : ordersItens) {

            Product product = orderIten.getProduct();

            table.addCell(new Phrase(String.valueOf( orderIten.getSequence() ), labelFont));
            table.addCell(new Phrase(product.getDescription(), labelFont));
            table.addCell(new Phrase(String.valueOf(orderIten.getQuantity()), labelFont));
            table.addCell(new Phrase(" R$ " + String.valueOf(orderIten.getSalePrice()), labelFont));
            table.addCell(new Phrase(" R$ " + String.valueOf(orderIten.getSalePrice() * orderIten.getQuantity()), labelFont));

        }


        BigDecimal total = new BigDecimal(order.getNetValue().toString());
        th = new PdfPCell(new Phrase("Total: R$ " + total.setScale(2, BigDecimal.ROUND_CEILING).toString()));
        th.setColspan(5);
        th.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(th);

        th = new PdfPCell();
        th.setColspan(5);
        th.setHorizontalAlignment(Element.ALIGN_LEFT);
        th.addElement(new Paragraph("Parcelas: " + order.getInstallment().getDescription()));

        Set<OrderPayment> parcelas = new TreeSet<>(order.getOrdersPayments());


        for (OrderPayment parcela : parcelas) {

            String formatDate = DateUtil.getFormatDate(parcela.getExpirationDate());

            BigDecimal valor = new BigDecimal(parcela.getInstallmentValue().toString());

            Paragraph paragraph = new Paragraph( ( parcela.getSequence() +1 )  + " - " + formatDate + " - R$ " + valor.setScale(2, BigDecimal.ROUND_CEILING).toString(), labelFont);
            th.addElement(paragraph);

        }

        table.addCell(th);

        th = new PdfPCell(new Phrase("Observação: " + order.getObservation()));
        th.setColspan(5);
        th.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(th);

        document.add(table);

    }

    private void geraGridTopo(Document subCatPart) throws DocumentException {

        Customer customer = order.getCustomer();

        PdfPTable tableCliente = new PdfPTable(1);
        tableCliente.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        String endereco = customer.getStreet() != null ? customer.getStreet() : "";
        endereco += customer.getNumber() != null ? " Nº " + customer.getNumber() : "";

        String fone = customer.getCellPhone() != null ? customer.getCellPhone() : customer.getHomePhone() != null ? customer.getHomePhone() : "";

        tableCliente.addCell("Cliente: " + customer.getName());
        tableCliente.addCell("Endereço: " + endereco);
        tableCliente.addCell("Fone: " + fone);
        tableCliente.addCell("Data: " + DateUtil.getFormatDateTime(order.getIssuanceTime()));

        PdfPTable table = new PdfPTable(2);

        PdfPCell logoCell = new PdfPCell(createImage());
        logoCell.setPaddingTop(10);
        logoCell.setPaddingBottom(10);

        logoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //logoCell.setBorder( PdfPCell.NO_BORDER );

        table.setWidths(new float[]{30, 70});

        table.addCell(logoCell);
        table.addCell(tableCliente);

        table.setWidthPercentage(100);
        //table.getDefaultCell().setBorder( PdfPCell.NO_BORDER );


        subCatPart.add(table);

    }


    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
