package com.makus.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class CameraActivity extends AppCompatActivity {
    Button bKamera, bWarna;
    ImageView ivHasil;
    TextView tvDeskripsi, tvWarna, tv1;

    private static final int CAMERA_REQ = 1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        bKamera=(Button) findViewById(R.id.b_kamera);
        bWarna=(Button) findViewById(R.id.b_warna);
        ivHasil=(ImageView) findViewById(R.id.iv_kamera);
        tvDeskripsi=(TextView) findViewById(R.id.tv_deskripsi);
        tvWarna=(TextView) findViewById(R.id.tv_warna);
        tv1=(TextView) findViewById(R.id.tv_1);

        bWarna.setVisibility(View.INVISIBLE);
        bKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengambil gambar
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQ);
            }
        });
    }
    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data){

        //Ketika kode hasil Activity-nya RESULT_OK (semua berjalan lancar),
        //dan ketika code yang me-request Activitynya adalah CAMERA_REQUEST,
        //Ambil data hasil Activity dan simpan sebagai bitmap
        //Kemudian tampilkan hasilnya di ImageView kita

        if (reqCode == CAMERA_REQ && resCode == RESULT_OK) {
            //konvert ke bmp
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            ivHasil.setImageBitmap(bitmap);

            Bitmap gambar = bitmap; //assign your bitmap here
            int redColors = 0;
            int greenColors = 0;
            int blueColors = 0;
            int pixelCount = 0;

            for (int y = 0; y < bitmap.getHeight(); y++)
            {
                for (int x = 0; x < bitmap.getWidth(); x++)
                {
                    int c = bitmap.getPixel(x, y);
                    pixelCount++;
                    redColors += Color.red(c);
                    greenColors += Color.green(c);
                    blueColors += Color.blue(c);
                }
            }
            // calculate average of bitmap r,g,b values
            int red = (redColors/pixelCount);
            int green = (greenColors/pixelCount);
            int blue = (blueColors/pixelCount);

            int[] warnaRGB=rataRGB(bitmap);
            float[] hsv=new float[3];
            //xxxxxxxx
            Color.RGBToHSV(warnaRGB[0],warnaRGB[1],warnaRGB[2],hsv);
            String nama,keterangan,n_sayur = null,k_gizi=null,manfaat=null, akg=null;
            if      (hsv[1] < 0.1 && hsv[2] > 0.5){
                nama="Putih";
                keterangan="Jenis makanan alami tidak berpigmen ini adalah sumber vitamin C, kalsium dan bisa membantu meningkatkan sistem imun tubuh";
                n_sayur="Bawang";
                k_gizi=" Prebiotik, Allicin, Pottasium, Kalium, Magnesium, Vitamin C";
                manfaat="Menurunkan gula darah dan memiliki sifat anti-inflamasi dan anti-bakteri";
                akg="";
            }
            else if (hsv[2] < 0.1){
                nama="Hitam";
                keterangan="Sayuran yang memiliki warna hitam jarang ditemui dan langka. Namun, masih ada beberapa jenis makan yang memiliki warna hitam seperti beras ketan hitam atau beras hitam, kacang hitam, jamur, dan buah zaitun.";
                n_sayur="";
                k_gizi="Seng, Kalsium, Zat Besi dan Selenium";
                manfaat="Mencegah penyakit diabetes dan penyakit jantung";
                akg="";
            }
            else {
                float deg = hsv[0];
                if      (deg >=   0 && deg <  20){
                    nama="Merah";
                    keterangan="Warna merah yang terdapat pada buah dan sayur dibentuk oleh antosianin dan likopen. Antosianin adalah antioksidan yang bisa mencegah infeksi dan kanker kantong kemih. Kandungan likopen adalah senyawa yang larut dalam minyak yang juga merupakan antioksidan yang mampu mengurangi risiko kanker dan penyakit jantung, dan berguna juga untuk menghambat kemunduran fungsi fisik dan mental sehingga orang tidak mudah pikun";
                    n_sayur="Cabai";
                    k_gizi="Likopen, Antioksidan, Vitamin A dan Vitamin E";
                    manfaat="Menurunkan kadar kolesterol jahat atau LDL dalam darah, Menjaga kesehatan jantung";
                    akg="";
                }
                else if (deg >=  20 && deg <  45){
                    nama="Orange";
                    keterangan="Karena senyawa ini larut dalam minyak, penyerapannya dalam tubuh bisa meningkat jika dikonsumsi bersamaan dengan makanan berlemak. Bisa juga dengan cara dimasak, sebab pemanasan selama beberapa menit akan memecah dinding sel pada sayur dan buah-buahan lalu membebaskan bentuk terikat dari senyawa tersebut.";
                    n_sayur="Wortel";
                    k_gizi="Beta dan Alfa Karoten, Vitamin C, Folate, Vitamin B";
                    manfaat="Mencegah gangguan pada kulit dan mata serta memeliharanya agar tetap sehat";
                    akg="";
                }
                else if (deg >=  45 && deg <  90){
                    nama="Kuning";
                    keterangan="Berbeda dengan warna orange, warna kuning ternyata memiliki manfaat berbeda bagi kesehatan. Seperti mencegah tumbuhnya sel kanker dan penyakit jantung, selain itu warna kuning juga dapat meningkatkan nafsu makan.";
                    n_sayur="Jagung";
                    k_gizi="Vitamin C, Karoten, Vitamin A, Folate, Vitamin B";
                    manfaat="Mencegah tumbuhnya sel kanker dan penyakit jantung";
                }
                else if (deg >=  90.0 && deg < 150.0){
                    nama="Hijau";
                    keterangan="Warna hijau yang ada pada buah dan sayuran hijau terbentuk oleh klorofil. Semakin pekat atau gelap warna hijau yang terdapat pada buah dan sayuran, maka kandungan vitamin dan mineralnya semakin banyak. Buah yang berwarna hijau banyak mengandung asam alegat yang ampuh menggempur berbagai bibit sel kanker. Asam alegat membantu menormalkan tekanan darah. Sayuran yang berwarna hijau banyak mengandung vitamin C dan B Kompleks, kandungan zat besi, kalsium, magnesium, fosfor, betakaroten, dan serat. Sayuran berwarna hijau ini sangat bermanfaat bagi kesehatan kulit";
                    n_sayur="Bayam";
                    k_gizi="Vitamin B, Lutein, Zat Besi, Kalsium, Magnesium, Asam Folat";
                    manfaat="Menghilangkan radikal bebas, Mengurangi kanker, Melindungi sel mata dari cahaya ultraviolet";
                    akg=
                            "<p>" +
                                    "<b>Informasi Nilai Gizi</b>"+
                                    "<br>\tTakaran saji 100 gram" +
                                    "<br>\tJumlah sajian per kemasan-" +
                                    "<br>\tJumlah Per Sajian" +
                                    "<br>\tEnergi total 371 Kkal Energi dari lemak 63 Kkal" +
                                    "<br>\t% AKG*" +
                                    "<br>\t<strong>Lemak Total</strong>7g 11%" +
                                    "<br>\tLemak Jenuh 0g 0%" +
                                    "<br>\tLemak Trans 4.8g" +
                                    "<br>\t<strong>Kolesterol</strong> 0mg 0%" +
                                    "<br>\t<strong>Natrium</strong>/Sodium 4mg 0%" +
                                    "<br>\t<strong>Karbohidrat Total</strong> 65g 22%" +
                                    "<br>\tSerat Pangan 7g 28%" +
                                    "<br>\tGula 1.7g" +
                                    "<br>\t<strong>Protein</strong> 3g 6%" +
                                    "<br>\tVitamin A 52.11%" +
                                    "<br>\tVitamin C 7%" +
                                    "<br>\tKalsium 15.9%" +
                                    "<br>\tZat Besi 42.22%" +
                                    "<br>\tMagnesium 62%" +
                                    "<br>\tVitamin B6 30%" +
                                    "<br>\tKalium 10.8%" +
                                    "<br><i>*Persen AKG berdasarkan kebutuhan energi 2000 Kkal. Kebutuhan energi anda mungkin lebih tinggi atau lebih rendah</i>" +
                                    "</p>";
                }
                /*else if (deg >= 150 && deg < 210){
                    nama="Cyan";
                    keterangan="";
                }*/
                else if (deg >= 150 && deg < 210){
                    nama="Hijau";
                    keterangan="Warna hijau yang ada pada buah dan sayuran hijau terbentuk oleh klorofil. Semakin pekat atau gelap warna hijau yang terdapat pada buah dan sayuran, maka kandungan vitamin dan mineralnya semakin banyak. Buah yang berwarna hijau banyak mengandung asam alegat yang ampuh menggempur berbagai bibit sel kanker. Asam alegat membantu menormalkan tekanan darah. Sayuran yang berwarna hijau banyak mengandung vitamin C dan B Kompleks, kandungan zat besi, kalsium, magnesium, fosfor, betakaroten, dan serat. Sayuran berwarna hijau ini sangat bermanfaat bagi kesehatan kulit";
                    n_sayur="Bayam";
                    k_gizi="Vitamin B, Lutein, Zat Besi, Kalsium, Magnesium, Asam Folat";
                    manfaat="Menghilangkan radikal bebas, Mengurangi kanker, Melindungi sel mata dari cahaya ultraviolet";
                    akg=
                            "<p>" +
                                    "<b>Informasi Nilai Gizi</b>"+
                                    "<br>\tTakaran saji 100 gram" +
                                    "<br>\tJumlah sajian per kemasan-" +
                                    "<br>\tJumlah Per Sajian" +
                                    "<br>\tEnergi total 371 Kkal Energi dari lemak 63 Kkal" +
                                    "<br>\t% AKG*" +
                                    "<br>\t<strong>Lemak Total</strong>7g 11%" +
                                    "<br>\tLemak Jenuh 0g 0%" +
                                    "<br>\tLemak Trans 4.8g" +
                                    "<br>\t<strong>Kolesterol</strong> 0mg 0%" +
                                    "<br>\t<strong>Natrium</strong>/Sodium 4mg 0%" +
                                    "<br>\t<strong>Karbohidrat Total</strong> 65g 22%" +
                                    "<br>\tSerat Pangan 7g 28%" +
                                    "<br>\tGula 1.7g" +
                                    "<br>\t<strong>Protein</strong> 3g 6%" +
                                    "<br>\tVitamin A 52.11%" +
                                    "<br>\tVitamin C 7%" +
                                    "<br>\tKalsium 15.9%" +
                                    "<br>\tZat Besi 42.22%" +
                                    "<br>\tMagnesium 62%" +
                                    "<br>\tVitamin B6 30%" +
                                    "<br>\tKalium 10.8%" +
                                    "<br><br><i>*Persen AKG berdasarkan kebutuhan energi 2000 Kkal. Kebutuhan energi anda mungkin lebih tinggi atau lebih rendah</i>" +
                                    "</p>";
                }
                else if (deg >= 210 && deg < 270){
                    nama="Ungu - Biru";
                    keterangan="Warna biru atau ungu yang ada pada buah dan sayur dibentuk oleh senyawa antosianin, berguna untuk menghambat terbentuknya gumpalan dalam pembuluh darah, sehingga risiko penyakit jantung dan stroke berkurang.";
                    n_sayur="Terong";
                    k_gizi="Anthosianin";
                    manfaat="Manfaat mencegah terjadinya inflamasi atau peradangan, mencegah kanker, melindungi sel dari kerusakan";
                    akg="";
                }
                else if (deg >= 270 && deg < 330){
                    nama="Ungu - Pink";
                    keterangan="Warna biru atau ungu yang ada pada buah dan sayur dibentuk oleh senyawa antosianin, berguna untuk menghambat terbentuknya gumpalan dalam pembuluh darah, sehingga risiko penyakit jantung dan stroke berkurang.";
                    n_sayur="Terong";
                    k_gizi="Anthosianin";
                    manfaat="Manfaat mencegah terjadinya inflamasi atau peradangan, mencegah kanker, melindungi sel dari kerusakan";
                    akg="";
                }
                else{
                    nama="Merah";
                    keterangan="Warna merah yang terdapat pada buah dan sayur dibentuk oleh antosianin dan likopen. Antosianin adalah antioksidan yang bisa mencegah infeksi dan kanker kantong kemih. Kandungan likopen adalah senyawa yang larut dalam minyak yang juga merupakan antioksidan yang mampu mengurangi risiko kanker dan penyakit jantung, dan berguna juga untuk menghambat kemunduran fungsi fisik dan mental sehingga orang tidak mudah pikun";
                    n_sayur="Cabai";
                    k_gizi="Likopen, Antioksidan, Vitamin A dan Vitamin E";
                    manfaat="Menurunkan kadar kolesterol jahat atau LDL dalam darah, Menjaga kesehatan jantung";
                    akg="";
                }
            }
            //bWarna.setBackgroundColor(Color.rgb(red, green, blue));
            bWarna.setBackgroundColor(Color.rgb(warnaRGB[0], warnaRGB[1], warnaRGB[2]));
            String br=System.getProperty("line.separator");
            String hasil=
                    "<html>"+
                        "<p style=\"text-align:justify;\">"+
                            "<b>Contoh Sayur</b>"+
                            "<br>\t"+n_sayur+
                        "</p>"+
                        "<p style=\"text-align:justify;\">"+
                            "<b>Kandungan Gizi</b>"+
                            "<br>\t"+k_gizi+
                        "</p>"+
                        "<p style=\"text-align:justify;\">"+
                            "<b>Manfaat</b>"+
                            "<br>\t"+manfaat+
                        "</p>"+
                        "<p style=\"text-align:justify;\">"+
                            "<b>Keterangan</b>"+
                            "<br>\t"+keterangan+
                        "</p>"+
                            akg+
                    "</html>";
            String warna=
                    "<html>"+
                         "<p style=\"text-align:justify;\">"+
                            "<b> </b>"+
                            "<br>\tNama\t=\t"+nama+
                            "<br>\tRGB\t=\t("+red+","+green+","+blue+")"+
                            "<br>\tHSV\t=\t"+hsv+
                            "<br>\tHue\t=\t"+hsv[0]+
                            "<br>\tSat\t=\t"+hsv[1]+
                            "<br>\tVal\t\t=\t"+hsv[2]+
                         "</p>"+
                    "</html>";
            String w1=
                    "<html>"+
                            "<b>Warna</b>"+
                    "</html>";
            tvDeskripsi.setText(Html.fromHtml(hasil));
            tvWarna.setText(Html.fromHtml(warna));
            tv1.setText(Html.fromHtml(w1));
            bWarna.setVisibility(View.VISIBLE);
        }
    }
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
    public static int[] rataRGB(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        int size = width * height;
        int pixelColor;
        int r, g, b;
        r = g = b = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = bitmap.getPixel(x, y);
                if (pixelColor == 0) {
                    size--;
                    continue;
                }
                r += Color.red(pixelColor);
                g += Color.green(pixelColor);
                b += Color.blue(pixelColor);
            }
        }
        r /= size;
        g /= size;
        b /= size;
        return new int[] {
                r, g, b
        };
    }
}
