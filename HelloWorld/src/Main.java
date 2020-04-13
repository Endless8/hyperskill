public class Main {

    Main() {}

    public static void main(String[] args) {
        Test obj = new Test() {
            {
                num = 4;
                text = "kafs";
            }
        };
        String[] a = new String[] {
               "asd","asd"
        };

        System.out.println(obj.num);
        System.out.println(8 % 5);
    }
}

class Asd {
    public int asd;

    public void asd(){}
}
