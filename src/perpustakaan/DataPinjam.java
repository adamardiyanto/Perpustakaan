/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan;

import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.*;
import java.time.Period;

/**
 *
 * @author ADAM ARDI
 */
public class DataPinjam extends JFrame {
    
 static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/perpustakaan?useLegacyDatetimeCode=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "qwerty123";
    
    Connection koneksi;
    Statement statement;       
    
JLabel lHead = new JLabel("DATA Peminjaman");    
JLabel lid = new JLabel("id = ");
JTextField tfid = new JTextField();
JLabel lNIS= new JLabel("NIS = ");
JTextField tfNIS = new JTextField();    
JLabel lNama = new JLabel("Nama = ");
JLabel tfNama = new JLabel();
JLabel lkelas = new JLabel("Kelas = ");
JLabel tfkelas = new JLabel();
JLabel lKode = new JLabel("Kode = ");
JTextField tfKode = new JTextField();
JLabel lJudul = new JLabel("Judul = ");
JLabel tfJudul = new JLabel("");
JLabel lPenerbit = new JLabel("Penerbit = ");
JLabel tfPenerbit = new JLabel("");
JLabel ltgl_pinjam = new JLabel("TGL pinjam = ");
JTextField tftgl_pinjam = new JTextField();
JLabel ltgl_kembali = new JLabel("TGL kembali = ");
JTextField tftgl_kembali = new JTextField();
JLabel lLama = new JLabel("lama = ");
JLabel tfLama = new JLabel("");

JButton btnSimpan = new JButton("SIMPAN");
JButton btnRefresh = new JButton("REFRESH");
JButton btnCari = new JButton("CARi");
JButton btnHapus = new JButton("HAPUS");
JButton btnPrint = new JButton("Print");
JButton btnKeluar = new JButton("Keluar");

JLabel lDenda = new JLabel("Denda = ");
JLabel tfDenda = new JLabel("");
JLabel lNIK = new JLabel("NIK = ");
JTextField tfNIK = new JTextField();
JLabel lPetugas = new JLabel("Petugas = ");
JLabel tfPetugas = new JLabel("");

JTable table;
DefaultTableModel tableModel;
JScrollPane scrollPane;
Object namaKolom[] = {"id_pinjam","NIS","Nama","kelas","Kode","Judul","Penerbit","tgl pinjam","tgl kembali","lama","denda","petugas","NIK"};


public DataPinjam(){
    
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
lHead.setBounds(300,10,100,100);
add(lid);
lid.setBounds(20,100, 100, 20);
add(tfid);
tfid.setBounds(120, 100, 100, 20);
add(lNIS);
lNIS.setBounds(20,130, 100, 20);
add(tfNIS);
tfNIS.setBounds(120, 130, 100, 20);
add(lNama);
lNama.setBounds(20, 160,100, 20);
add(tfNama);
tfNama.setBounds(120, 160,100, 20);
add(lkelas);
lkelas.setBounds(20,190, 100, 20);
add(tfkelas);
tfkelas.setBounds(120, 190, 100, 20);
add(lKode);
lKode.setBounds(20,220, 100, 20);
add(tfKode);
tfKode.setBounds(120, 220, 100, 20);
add(lJudul);
lJudul.setBounds(20,250, 100, 20);
add(tfJudul);
tfJudul.setBounds(120, 250, 100, 20);
add(lPenerbit);
lPenerbit.setBounds(20,280, 100, 20);
add(tfPenerbit);
tfPenerbit.setBounds(120, 280, 100, 20);
add(ltgl_pinjam);
ltgl_pinjam.setBounds(20,310, 100, 20);
add(tftgl_pinjam);
tftgl_pinjam.setBounds(120, 310, 100, 20);
add(ltgl_kembali);
ltgl_kembali.setBounds(20,340, 100, 20);
add(tftgl_kembali);
tftgl_kembali.setBounds(120, 340, 100, 20);
add(lLama);
lLama.setBounds(20,370, 100, 20);
add(tfLama);
tfLama.setBounds(120, 370, 100, 20);

add(btnSimpan);
btnSimpan.setBounds(400,100,80,40);
add(btnRefresh);
btnRefresh.setBounds(500,100,80,40);
add(btnCari);
btnCari.setBounds(400,150,80,40);
add(btnHapus);
btnHapus.setBounds(500,150,80,40);
add(btnPrint);
btnPrint.setBounds(400,200,80,40);
add(btnKeluar);
btnKeluar.setBounds(500,200,80,40);

add(lDenda);
lDenda.setBounds(400,310, 100, 20);
add(tfDenda);
tfDenda.setBounds(500, 310, 100, 20);
add(lNIK);
lNIK.setBounds(400,340, 100, 20);
add(tfNIK);
tfNIK.setBounds(500, 340, 100, 20);
add(lPetugas);
lPetugas.setBounds(400,370, 100, 20);
add(tfPetugas);
tfPetugas.setBounds(500, 370, 100, 20);

add(scrollPane);
scrollPane.setBounds(20,400,760,200);
scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);


if (this.getBanyakData() != 0) { 
            String dataPinjam[][] = this.readPinjam(); 
            table.setModel((new JTable(dataPinjam, namaKolom)).getModel());
          
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ada");
        }
btnSimpan.addActionListener((ActionEvent e) -> {
            if (tfKode.getText().equals("") ) {
                JOptionPane.showMessageDialog(null, "Field tidak boleh kosong");
            } else {
                String id = tfid.getText();
                String nis = tfNIS.getText();
                String kode = tfKode.getText();
                String tgl_pinjam = tftgl_pinjam.getText();
                String tgl_kembali = tftgl_kembali.getText();
                Date tglAwal = (Date) Date.valueOf(tgl_pinjam);
                Date tglAkhir = (Date) Date.valueOf(tgl_kembali);
                long lama = Math.abs(tglAkhir.getTime() - tglAwal.getTime());
                long denda = (lama - 7)*1000; 
                String nik = tfNIK.getText();
                this.insertBuku(id, nis, kode, tgl_pinjam, tgl_kembali,lama,denda, nik);
  
                String dataPinjam[][] = this.readPinjam();
                table.setModel(new JTable(dataPinjam,namaKolom).getModel());
            }
        });

table.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(MouseEvent e){ 
               int baris = table.getSelectedRow();
               int kolom = table.getSelectedColumn(); 
               String dataterpilih = table.getValueAt(baris, 0).toString();
               btnHapus.addActionListener((ActionEvent f) -> {
                  deletePinjam(dataterpilih);
                  String dataPinjam[][] = readPinjam();
                table.setModel(new JTable(dataPinjam,namaKolom).getModel());
                });
               
           }
        });


btnRefresh.addActionListener((ActionEvent e) -> {
          tfid.setText("");
          tfNIS.setText("");
          tfKode.setText("");
          tftgl_pinjam.setText("");
          tftgl_kembali.setText("");
          tfNIK.setText("");
          
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
            String query = "SELECT * from `peminjaman`";
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

String[][] readPinjam() {
        try{
            int jmlData = 0;
            String data[][]=new String[getBanyakData()][13];
            String query = "Select `id_pinjam`,`NIS_p`,`Nama`,`kelas`,`Kode_buku_p`,`Judul`,`Penerbit`,`tgl_pinjam`,`tgl_kembali`,`Lama`,`Denda`,`Nama_k`,`NIK_p` from `peminjaman` JOIN `buku` ON `Kode_buku_p`=`Kode_buku` JOIN `karyawan` ON `NIK_p`=`NIK` JOIN `anggota` ON `NIS_p`=`NIS`";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                data[jmlData][0] = resultSet.getString("id_pinjam");
                data[jmlData][1] = resultSet.getString("NIS_p");
                data[jmlData][2] = resultSet.getString("Nama");
                data[jmlData][3] = resultSet.getString("kelas");
                data[jmlData][4] = resultSet.getString("Kode_buku_p");
                data[jmlData][5] = resultSet.getString("Judul");
                data[jmlData][6] = resultSet.getString("Penerbit");
                data[jmlData][7] = resultSet.getString("tgl_pinjam");
                data[jmlData][8] = resultSet.getString("tgl_kembali");
                data[jmlData][9] = resultSet.getString("Lama");
                data[jmlData][10] = resultSet.getString("Denda");
                data[jmlData][11] = resultSet.getString("Nama_k");
                data[jmlData][12] = resultSet.getString("NIK_p");
                jmlData++;
            }
            return data;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            System.out.println("SQL error");
            return null;
        }
    }

public void insertBuku(String id, String nis, String kode, String tgl_pinjam, String tgl_kembali, long lama, long denda, String nik) {
        try{
            String query = "INSERT INTO `peminjaman`(`id_pinjam`,`NIS_p`,`Kode_buku_p`,`tgl_pinjam`,`tgl_kembali`,`Lama`,`Denda`,`NIK_p`) VALUES ('"+id+"','"+nis+"','"+kode+"', '"+tgl_pinjam+"','"+tgl_kembali+"','"+lama+"','"+denda+"','"+nik+"')";
        statement = (Statement) koneksi.createStatement();
        statement.executeUpdate(query);
        System.out.println("Berhasil Ditambahkan");
        JOptionPane.showMessageDialog(null,"data berhasil ditambahkan");
        }catch(Exception sql){
            System.out.println(sql.getMessage());
            JOptionPane.showMessageDialog(null, sql.getMessage());
        }
        
    }

void deletePinjam(String id) {
        try{
            String query = "DELETE FROM `peminjaman` WHERE `id_pinjam` = '"+id+"'";
            statement = koneksi.createStatement();
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "berhasil dihapus" );
        }catch(SQLException sql){
            System.out.println(sql.getMessage());
        }
    }


}
