import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Path to the path to the Hangouts.json file: ");
        String filepath = scanner.nextLine();

        System.out.print("Output directory: ");
        String outDir = scanner.nextLine();

        new Transcriber(filepath, outDir);
    }
}
