package membership;

import java.util.ArrayList;
import java.util.Iterator;

public class Membership {
    public ArrayList<MemberData> list = new ArrayList<>();

    public void tambah(MemberData data) throws Exception {
        this.list.add(data);
        try {
            tambahPoin(data.nama);
        } catch (Exception e) {
            throw e;
        }
    }

    public void tambahPoin(String nama) throws Exception {
        Iterator<MemberData> iList = this.list.iterator();
        MemberData chosen = null;

        int givenPoint = 1;

        while (iList.hasNext()) {
            MemberData item = iList.next();

            if (item.nama.toLowerCase().equals(nama.toLowerCase())) {
                chosen = item;
                break;
            }
        }

        if (chosen == null)
            throw new Exception("Chosen bernilai null");

        if (chosen.jumlahTransaksi > 0 && chosen.jumlahTransaksi % 10 == 0) {
            if (chosen.kode.toLowerCase().contains("a")) {
                givenPoint *= 2;
            }

            chosen.jumlahPoin += givenPoint;
        }

        chosen.jumlahTransaksi += 1;
    }

    public void kurangiPoin(String nama, int jumlahPoin) throws Exception {
        Iterator<MemberData> iList = this.list.iterator();
        MemberData chosen = null;
        int index = 0;

        while (iList.hasNext()) {
            MemberData item = iList.next();

            if (item.nama.equalsIgnoreCase(nama)) {
                chosen = item;
                break;
            }

            index += 1;
        }

        if (chosen == null)
            throw new Exception("Chosen bernilai null");

        MemberData dataToBeChanged = this.list.get(index);

        if (dataToBeChanged.jumlahPoin < jumlahPoin)
            throw new Exception();
        dataToBeChanged.jumlahPoin -= jumlahPoin;
    }

    public int getPoin(String nama) {
        Iterator<MemberData> iter = this.list.iterator();

        while (iter.hasNext()) {
            MemberData item = iter.next();
            if (item.nama.equalsIgnoreCase(nama)) {
                return item.jumlahPoin;
            }
        }

        return 0;
    }

    public boolean kodeMengandungHurufA(String nama) {
        Iterator<MemberData> iter = this.list.iterator();

        while (iter.hasNext()) {
            MemberData item = iter.next();
            if (item.nama.toLowerCase().equals(nama.toLowerCase())) {
                return item.kode.toLowerCase().contains("a");
            }
        }

        return false;
    }
}