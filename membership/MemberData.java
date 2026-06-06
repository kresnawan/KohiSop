package membership;

import java.security.SecureRandom;

public class MemberData {
    public String kode;
    public String nama;
    public int jumlahPoin = 0;
    public int jumlahTransaksi = 0;

    public MemberData(String nama) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 6) {
            sb.append(Integer.toHexString(random.nextInt()));
        }
        this.kode = sb.toString().toUpperCase().substring(0, 6);

        this.nama = nama;
    }
}
