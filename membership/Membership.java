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
        int index = 0;

        while (iList.hasNext()) {
            MemberData item = iList.next();

            if (item.nama.toLowerCase().equals(nama.toLowerCase())) {
                chosen = item;
                break;
            }

            index += 1;
        }

        if (chosen == null)
            throw new Exception("Chosen bernilai null");
        MemberData dataToBeChanged = this.list.get(index);
        dataToBeChanged.jumlahTransaksi += 1;

        if (dataToBeChanged.jumlahTransaksi % 3 == 0) {
            if (chosen.kode.toLowerCase().contains("a")) {
                givenPoint *= 2;
            }

            dataToBeChanged.jumlahPoin += givenPoint;
        }
    }

    public void kurangiPoin(String nama, int jumlahPoin) throws Exception {
        Iterator<MemberData> iList = this.list.iterator();
        MemberData chosen = null;
        int index = 0;

        while (iList.hasNext()) {
            MemberData item = iList.next();

            if (item.nama.toLowerCase().equals(nama.toLowerCase())) {
                chosen = item;
                break;
            }

            index += 1;
        }

        if (chosen == null)
            throw new Exception("Chosen bernilai null");

        MemberData dataToBeChanged = this.list.get(index);
        
        if (dataToBeChanged.jumlahPoin < jumlahPoin) throw new Exception();
        dataToBeChanged.jumlahPoin -= jumlahPoin;
    }

    public int getPoin(String nama) {
        Iterator<MemberData> iter = this.list.iterator();

        while (iter.hasNext()) {
            MemberData item = iter.next();
            if (item.nama.toLowerCase().equals(nama.toLowerCase())) {
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
                boolean res = item.kode.contains("A") ? true : false;
                return res;
            }
        }

        return false;
    }
}