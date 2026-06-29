package util;

/**
 *
 * @author danie
 */
public class CSVUtil {
    
    public static int[][] convertirTextoAMatriz(String texto) {
        String[] valores = texto.split(";");
        int total = valores.length;
        int n = (int) Math.sqrt(total);

        if (n * n != total) {
            return new int[0][0];
        }

        int[][] matriz = new int[n][n];
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriz[i][j] = Integer.parseInt(valores[index].trim());
                index++;
            }
        }

        return matriz;
    }
}
