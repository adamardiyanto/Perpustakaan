/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpustakaan;

/**
 *
 * @author ADAM ARDI
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;

        
public class Perpustakaan {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Awal awal = new Awal();
        //DataKaryawan d = new DataKaryawan ();
    }
    
    
}

class Awal extends JFrame {
    JButton btnBuku = new JButton("Data Buku");
    JButton btnAnggota = new JButton("Data Anggota");
    JButton btnKaryawan = new JButton("Data Karyawan");
    JButton btnPinjam = new JButton("Data Peminjaman");
    
    public Awal(){
        setTitle("BERANDA");
	setDefaultCloseOperation(3);
	setSize(500,400);
        setLocation(500,275);
        
        setLayout(new GridLayout(2,2));
        add(btnAnggota);
        add(btnBuku);
        add(btnKaryawan);
        add(btnPinjam);
        setVisible(true);
        
        btnAnggota.addActionListener((ActionEvent e) -> {
           DataAnggota anggota = new DataAnggota();
           dispose();
        });
        
        btnBuku.addActionListener((ActionEvent e) -> {
           DataBuku buku = new DataBuku();
           dispose();
        });
        
        btnKaryawan.addActionListener((ActionEvent e) -> {
           DataKaryawan karyawan = new DataKaryawan();
           dispose();
        });
        
        btnPinjam.addActionListener((ActionEvent e) -> {
           DataPinjam pinjam = new DataPinjam();
           dispose();
        });
    }
}
