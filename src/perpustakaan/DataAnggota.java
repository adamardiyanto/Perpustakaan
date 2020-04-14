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
public class DataAnggota extends JFrame {
    
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan?useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "qwerty123";
    
    Connection koneksi;
    Statement statement;

JLabel lJudul = new JLabel("DATA ANGGOTA");
JLabel lNIS = new JLabel("NIS = ");
JTextField tfNIS = new JTextField();
JLabel lNama= new JLabel("Nama = ");
JTextField tfNama = new JTextField();    
JLabel lTTL = new JLabel("TTL = ");
JTextField tfTTL = new JTextField();
JLabel lJenkel = new JLabel("jenis kelamin = ");
JCheckBox cbPerempuan = new JCheckBox(" Perempuan ");
JCheckBox cbLaki = new JCheckBox(" laki laki");
JLabel lAgama = new JLabel(" Agama ");
   String[] namaAgama =
            {"Islam","Kristen","Katolik","Hindu","Budha"};
JComboBox cmbAgama = new JComboBox(namaAgama);
JLabel ltgl_daftar= new JLabel("tanggal daftar = ");
JTextField tftgl_daftar = new JTextField();
JLabel ltgl_akhir= new JLabel("tanggal berakhir = ");
JTextField tftgl_akhir = new JTextField();
JLabel lkelas= new JLabel("kelas = ");
JTextField tfkelas = new JTextField();
JButton btnSimpan = new JButton("SIMPAN");
JButton btnHapus = new JButton("HAPUS");
JButton btnKeluar = new JButton("keluar");
JButton btnRefresh = new JButton("refresh");
JTable table;
DefaultTableModel tableModel;
JScrollPane scrollPane;
Object namaKolom[] = {"NIS","Nama","TTL","Jenkel","Agama","tgl_daftar","tgl_akhir","kelas"};

public DataAnggota(){
    
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

add(lJudul);
lJudul.setBounds(300,10,200,100);
//lJudul.setFont((Font) Bold);
add(lNIS);
lNIS.setBounds(30, 120,100, 30);
add(tfNIS);
tfNIS.setBounds(250, 120,100, 30);
add(lNama);
lNama.setBounds(30, 160,100, 30);
add(tfNama);
tfNama.setBounds(250, 160,100, 30);
add(lTTL);
lTTL.setBounds(30, 200,100, 30);
add(tfTTL);
tfTTL.setBounds(250, 200,100, 30);
add(lJenkel);
lJenkel.setBounds(30, 240,100, 30);
add(cbPerempuan);
cbPerempuan.setBounds(150, 240,100, 30);
add(cbLaki);
cbLaki.setBounds(250, 240,100, 30);

add(lAgama);
lAgama.setBounds(400,120,100,30);
add(cmbAgama);
cmbAgama.setBounds(650,120,100,30);
add(lkelas);
lkelas.setBounds(400,160,100,30);
add(tfkelas);
tfkelas.setBounds(650,160,100,30);
add(ltgl_daftar);
ltgl_daftar.setBounds(400,200,100,30);
add(tftgl_daftar);
tftgl_daftar.setBounds(650,200,100,30);
add(ltgl_akhir);
ltgl_akhir.setBounds(400,240,100,30);
add(tftgl_akhir);
tftgl_akhir.setBounds(650,240,100,30);

add(btnSimpan);
btnSimpan.setBounds(150,300,100,50);
add(btnHapus);
btnHapus.setBounds(300,300,100,50);
add(btnKeluar);
btnKeluar.setBounds(450,300,100,50);
add(btnRefresh);
btnRefresh.setBounds(600,300,100,50);

add(scrollPane);
scrollPane.setBounds(20,400,760,200);
scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

if (this.getBanyakData() != 0) { // kalau banyak datanya tidfak sama dengan 0
            String dataAnggota[][] = this.readAnggota(); //ambil method readMahasiswa di model
            table.setModel((new JTable(dataAnggota, namaKolom)).getModel());
            //menampilkan data yang ada didalam database ke tabel
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnSimpan.addActionListener((ActionEvent e) -> {
            if (tfNIS.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String nis = tfNIS.getText();
                String nama = tfNama.getText();
                String ttl = tfTTL.getText();
                String jenkel = null;
                if (cbPerempuan.isSelected()) {
                    jenkel = "perempuan";
                } else if (cbLaki.isSelected() ) {
                    jenkel = "Laki laki";
                }
                String agama = (String) cmbAgama.getSelectedItem();
                String kelas = tfkelas.getText();
                String tgl_daftar = tftgl_daftar.getText();
                String tgl_akhir = tftgl_akhir.getText();
                
                
                
                this.insertAnggota(nis, nama, ttl, jenkel, agama, tgl_daftar, tgl_akhir, kelas);
  
                String dataAnggota[][] = this.readAnggota();
                table.setModel(new JTable(dataAnggota,namaKolom).getModel());
            }
        });
//String data =null;

table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = table.getSelectedRow();
               int kolom = table.getSelectedColumn(); // ga kepake
               //ngambil nim yang ada di kolom inddex 0
               String dataterpilih = table.getValueAt(baris, 0).toString();
               btnHapus.addActionListener((ActionEvent f) -> {
                  deleteAnggota(dataterpilih);
                  String dataAnggota[][] = readAnggota();
                table.setModel(new JTable(dataAnggota,namaKolom).getModel());
                });
               
           }
        });


btnRefresh.addActionListener((ActionEvent e) -> {
          tfNama.setText("");
          tfNIS.setText("");
          tfTTL.setText("");
          tftgl_daftar.setText("");
          tftgl_akhir.setText("");
          tfkelas.setText("");
          
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
            String query = "SELECT * from `anggota`";
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

String[][] readAnggota() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][8];
            String query = "Select * from `anggota`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("NIS");
                data[jmlData][1] = resultSet.getString("Nama");
                data[jmlData][2] = resultSet.getString("TTL");
                data[jmlData][3] = resultSet.getString("Jenkel");
                data[jmlData][4] = resultSet.getString("Agama");
                data[jmlData][5] = resultSet.getString("tgl_daftar");
                data[jmlData][6] = resultSet.getString("tgl_akhir");
                data[jmlData][7] = resultSet.getString("kelas");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

public void insertAnggota(String nis, String nama, String ttl, String jenkel, String agama, String tgl_daftar, String tgl_akhir, String kelas) {
        try{
            String query = "INSERT INTO `anggota`(`NIS`,`Nama`,`TTL`,`Jenkel`,`Agama`,`tgl_daftar`,`tgl_akhir`,`kelas`) VALUES ('"+nis+"','"+nama+"','"+ttl+"', '"+jenkel+"','"+agama+"','"+tgl_daftar+"','"+tgl_akhir+"','"+kelas+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"data berhasil ditambahkan");
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
        
    }

void deleteAnggota(String nis) {
        try{
            String query = "DELETE FROM `anggota` WHERE `NIS` = '"+nis+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus"+nis );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }



}
