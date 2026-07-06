import java.util.Scanner;

class EliminasiGaus {
    private float[][] matriks;
    public EliminasiGaus(){}
    public EliminasiGaus(float[][] matriks){this.matriks = matriks;}
    public void setMatriks(float[][] matriks){this.matriks = matriks;}
    public float[][] getMatriks(){return matriks;}
    public void printMatriks() {
        for (float[] baris : matriks) {
            System.out.print("[ ");
            int j = 0;
            int kolomTerakhir = baris.length - 1;
            for (float nilai : baris) {
                if (j == kolomTerakhir)
                    System.out.print("| ");
                System.out.printf("%5.1f ", nilai);
                j++;
            }
            System.out.println("]");
        }
    }
    public float[] runGaus() {
        int n = matriks.length;
        for (int i = 0; i < n; i++) {
            int maxRow = i;
            for (int k = i + 1; k < n; k++) {
                if (Math.abs(matriks[k][i]) > Math.abs(matriks[maxRow][i])) {
                    maxRow = k;
                }
            }
            float[] temp = matriks[i];
            matriks[i] = matriks[maxRow];
            matriks[maxRow] = temp;
            if (matriks[i][i] == 0) {
                System.err.println("\n[Peringatan] Data yang dimasukkan tidak logis atau tidak memiliki solusi unik (Matriks Singular).");
                return null;
            }
            for (int k = i + 1; k < n; k++) {
                float faktor = matriks[k][i] / matriks[i][i];
                for (int j = i; j <= matriks[i].length - 1; j++) {
                    matriks[k][j] = matriks[k][j] - faktor * matriks[i][j];
                }
            }
        }
        printMatriks();
        float[] hasil = new float[n];
        for (int i = n - 1; i >= 0; i--) {
            hasil[i] = matriks[i][n];
            for (int j = i + 1; j < n; j++)
                hasil[i] = hasil[i] - matriks[i][j] * hasil[j];
            hasil[i] = hasil[i] / matriks[i][i];
        }

        return hasil;
    }
}
public class PaketPeralatanTulis {
    private static String TITLE_APP = "Paket Peralatan Tulis Gaus";
    private static final String GOALS_APP = "Agar lebih mudah saat memulai bisnis dan ingin menentukan harga satuan untuk sebuah barang,\natau bagi pembeli yang ingin tahu harga satuannya, mereka bisa menggunakan aplikasi ini.";
    private static final String[] NAMA_BARANG = {"Buku Tulis", "Pulpen", "Pensil"};
    private static EliminasiGaus inputPackagePrice() {
        float[][] matriks = null;
        EliminasiGaus gaus = null;
        try (var scanner = new Scanner(System.in)) {
            try {
                System.out.print("Masukan total paket: ");
                var jumlahPaket = Integer.parseInt(scanner.nextLine());
                matriks = new float[jumlahPaket][4];
                for (var i = 0; i < matriks.length; ++i) {
                    System.out.printf("[+] Paket Alat Tulis Ke-%d\n", i+1);
                    System.out.print("[-] Masukan jumlah buku: ");
                    var jumlahBuku = Float.parseFloat(scanner.nextLine());
                    System.out.print("[-] Masukan jumlah pulpen: ");
                    var jumlahPulpen = Float.parseFloat(scanner.nextLine());
                    System.out.print("[-] Masukan jumlah pensil: ");
                    var jumlahPensil = Float.parseFloat(scanner.nextLine());
                    System.out.print("[-] Masukan total harga (Rp. 1.000 => 1): ");
                    var totalHarga = Float.parseFloat(scanner.nextLine());
                    matriks[i][0] = jumlahBuku;
                    matriks[i][1] = jumlahPulpen;
                    matriks[i][2] = jumlahPensil;
                    matriks[i][3] = totalHarga;
                    System.out.println();
                }
                gaus = new EliminasiGaus(matriks);
            } catch (NumberFormatException e) {
                System.err.println("Error: Input yang di masukan harus berupa angka!");
            }
        }
        return gaus;
    }
    private static void printResult(float[] resultGaus) {
        if (resultGaus == null)
            System.exit(1);

        System.out.println("\n=== Harga Satuan ===");
        for (int i = 0; i < resultGaus.length; i++) {
            int hargaFinal = (int) Math.round(resultGaus[i] * 1000);
            System.out.printf("Harga 1 %s = Rp. %d\n", NAMA_BARANG[i], hargaFinal);
        }
    }
    public static void main(String[] args){
        System.out.printf("\n\t%s\n\n", TITLE_APP);
        System.out.printf("Tujuan aplikasi:\n%s\n\n", GOALS_APP);
        var gaus = inputPackagePrice();
        if (gaus == null) System.exit(1);
        gaus.printMatriks();
        System.out.println();
        printResult(gaus.runGaus());
    }
}