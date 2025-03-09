package com.example.belajar;

import javafx.scene.shape.StrokeLineCap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private ArrayList<Mahasiswa> Data = new ArrayList<>();
    private String filename = "src/DATA.csv";
    private Path path = Path.of(filename);

    public Database() {
        open();
    }

    public ArrayList<Mahasiswa> getData() {
        return Data;
    }

    public void open() {
        try {
            List<String> lines = Files.readAllLines(path);
            Data = new ArrayList<>();
            for (int i = 1; i <lines.size(); i++) {
                String line = lines.get(i);
                String[]element= line.split(",");
                if (element.length >= 6) { // Pastikan baris memiliki minimal 6 elemen
                    String nim = element[0];
                    String nama = element[1];
                    String alamat = element[2];
                    int semester = Integer.parseInt(element[3]);
                    int sks = Integer.parseInt(element[4]);
                    double ipk = Double.parseDouble(element[5]);
                    Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
                    Data.add(mhs);
                } else {
                    System.err.println("Baris tidak lengkap: " + line); // Tampilkan pesan error
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(){
        StringBuilder sb = new StringBuilder();
        sb.append("NIM,NAMA,ALAMAT (KOTA), SEMESTER, SKS, IPK\n");
        if (!Data.isEmpty()){
            for (int i = 0; i < Data.size(); i++) {
                Mahasiswa mhs = Data.get(i);
                String line = mhs.getNim() + "," + mhs.getNama() + "," + mhs.getAlamat() + "," + mhs.getSemester() + "," + mhs.getSks() + "," + mhs.getIpk() + "\n";
                sb.append(line);
            }
        }
        try {
            Files.writeString(path,sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void view(){
        System.out.println("========================================================================================");
        System.out.printf("| %-8.8S  |", "NIM");
        System.out.printf(" %-20.20S |", "NAMA");
        System.out.printf(" %-20.20S |", "ALAMAT");
        System.out.printf(" %8.8S    |", "SEMESTER");
        System.out.printf(" %3.3S    |", "SKS");
        System.out.printf(" %4.4S    |%n", "IPK");
        System.out.println("----------------------------------------------------------------------------------------");
        for (Mahasiswa mhs : Data){
            System.out.printf("| %-8.8S  |", mhs.getNim());
            System.out.printf(" %-20.20S |", mhs.getNama());
            System.out.printf(" %-20.20S |", mhs.getAlamat());
            System.out.printf(" %8.8S    |", mhs.getSemester());
            System.out.printf(" %3.3S    |", mhs.getSks());
            System.out.printf(" %4.4S    |%n", mhs.getIpk());

        }
        System.out.println("----------------------------------------------------------------------------------------");
    }

    public boolean insert(String nim, String nama, String alamat, int semester, int sks, double ipk){
        boolean status = true;
        //cek primary key
        if (!Data.isEmpty()){
            for (int i = 0; i < Data.size(); i++) {
                if (Data.get(i).getNim().equalsIgnoreCase(nim)){
                    status = false;
                    break;
                }
            }

        }
        if (status == true){
            Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
            Data.add(mhs);
            save();
            return true;
        }
        return false;
    }

    public int search(String nim){
        int index = -1;
        if (!Data.isEmpty()){
            for (int i = 0; i < Data.size(); i++) {
                if (Data.get(i).getNim().equalsIgnoreCase(nim)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk) {
        boolean status = true;
        if (!Data.isEmpty()) {
            for (int i = 0; i < Data.size(); i++) {
                if (Data.get(i).getNim().equalsIgnoreCase(nim)) {
                    save();
                    status = false;
                    break;
                }
            }
        }
        return status;
    }
    public boolean delete(int index){
        boolean status = false;
        if (!Data.isEmpty()){
            Data.remove(index);
            save();
            status = true;
        }
        return status;
    }

}

