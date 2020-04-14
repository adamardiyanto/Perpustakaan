/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan;

import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author ADAM ARDI
 */
public class DataBuku extends JFrame {
static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan?useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "qwerty123";
    
    Connection koneksi;
    Statement statement;    
    
    
JLabel lHead = new JLabel("DATA BUKU");
JLabel lKode = new JLabel("Kode buku = ");
JTextField tfKode = new JTextField();
JLabel lJudul= new JLabel("Judul Buku = ");
JTextField tfJudul = new JTextField();    
JLabel lPengarang = new JLabel("Pengarang = ");
JTextField tfPengarang = new JTextField();
JLabel lPenerbit= new JLabel("Penerbit = ");
JTextField tfPenerbit = new JTextField(); 
JLabel lTahun= new JLabel("Tahun Terbit = ");
JTextField tfTahun = new JTextField(); 

JButton btnSimpan = new JButton("SIMPAN");
JButton btnHapus = new JButton("HAPUS");
JButton btnKeluar = new JButton("Keluar");
JButton btnRefresh = new JButton("REFRESH");
JButton btnCari = new JButton("CARi");

JTable table;
DefaultTableModel tableModel;
JScrollPane scrollPane;
Object namaKolom[] = {"Kode_buku","Judul","Pengarang","Penerbit","Tahun"};

public DataBuku(){
    
try{
            Class.forName(JDBC_DRIVER);
            koneksi = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Koneksi Berhasil");
        }catch(ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            System.out.println("Koneksi Gagal");
        }        
    
tableModel = new DefaultTableModel(namaKolom,0);
table = new JTable(tableModel);
scrollPane = new JScrollPane(table);

setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
setLayout(null);
setSize(800, 600);

add(lHead);
lHead.setBounds(300,10,200,100);

add(lKode);
lKode.setBounds(30, 120,100, 30);
add(tfKode);
tfKode.setBounds(150, 120,100, 30);
add(lJudul);
lJudul.setBounds(30, 160,100, 30);
add(tfJudul);
tfJudul.setBounds(150, 160,100, 30);
add(lPengarang);
lPengarang.setBounds(30, 200,100, 30);
add(tfPengarang);
tfPengarang.setBounds(150, 200,100, 30);
add(lPenerbit);
lPenerbit.setBounds(30, 240,100, 30);
add(tfPenerbit);
tfPenerbit.setBounds(150, 240,100, 30);
add(lTahun);
lTahun.setBounds(30, 280,100, 30);
add(tfTahun);
tfTahun.setBounds(150, 280,100, 30);

add(btnSimpan);
btnSimpan.setBounds(450,150,80,50);
add(btnHapus);
btnHapus.setBounds(560,150,80,50);
add(btnCari);
btnCari.setBounds(400,210,80,50);
add(btnKeluar);
btnKeluar.setBounds(500,210,80,50);
add(btnRefresh);
btnRefresh.setBounds(600,210,80,50);


add(scrollPane);
scrollPane.setBounds(20,350,760,200);
scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

if (this.getBanyakData() != 0) { // kalau banyak datanya tidfak sama dengan 0
            String dataBuku[][] = this.readBuku(); //ambil method readMahasiswa di model
            table.setModel((new JTable(dataBuku, namaKolom)).getModel());
            //menampilkan data yang ada didalam database ke tabel
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnSimpan.addActionListener((ActionEvent e) -> {
            if (tfKode.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String kode = tfKode.getText();
                String judul = tfJudul.getText();
                String pengarang = tfPengarang.getText();
                String penerbit = tfPenerbit.getText();
                String tahun = tfTahun.getText();
    
                this.insertBuku(kode, judul, pengarang, penerbit, tahun);
  
                String dataBuku[][] = this.readBuku();
                table.setModel(new JTable(dataBuku,namaKolom).getModel());
            }
        });
//String data =null;

table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = table.getSelectedRow();
               int kolom = table.getSelectedColumn(); 
               String dataterpilih = table.getValueAt(baris, 0).toString();
               btnHapus.addActionListener((ActionEvent f) -> {
                  deleteBuku(dataterpilih);
                  String dataBuku[][] = readBuku();
                table.setModel(new JTable(dataBuku,namaKolom).getModel());
                });
               
           }
        });


btnRefresh.addActionListener((ActionEvent e) -> {
          tfKode.setText("");
          tfJudul.setText("");
          tfPengarang.setText("");
          tfPenerbit.setText("");
          tfTahun.setText("");
         
          
        });

btnKeluar.addActionListener((ActionEvent e) -> {
          Awal awal = new Awal();
           dispose();
        });

}

int getBanyakData() {
        int jmlData = 0;
        try{
            statement = koneksi.createStatement();
            String query = "SELECT * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                jmlData++;
            }
            return jmlData;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return 0;
        }
    }

String[][] readBuku() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][5];
            String query = "Select * from `buku`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("Kode_buku");
                data[jmlData][1] = resultSet.getString("Judul");
                data[jmlData][2] = resultSet.getString("Pengarang");
                data[jmlData][3] = resultSet.getString("Penerbit");
                data[jmlData][4] = resultSet.getString("Tahun");
                
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

public void insertBuku(String kode, String judul, String pengarang, String penerbit, String tahun) {
        try{
            String query = "INSERT INTO `buku`(`Kode_buku`,`Judul`,`Pengarang`,`Penerbit`,`Tahun`) VALUES ('"+kode+"','"+judul+"','"+pengarang+"', '"+penerbit+"','"+tahun+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"data berhasil ditambahkan");
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
        
    }

void deleteBuku(String kode) {
        try{
            String query = "DELETE FROM `buku` WHERE `Kode_buku` = '"+kode+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }

}
