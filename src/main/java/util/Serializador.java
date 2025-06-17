package util;

import java.io.*;
import java.util.List;

public class Serializador {
    @SuppressWarnings("unchecked")
    public static <T> void salvar(String caminho, List<T> lista) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(caminho))) {
            o.writeObject(lista);
        } catch (Exception e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> carregar(String caminho) {
        try (ObjectInputStream o = new ObjectInputStream(new FileInputStream(caminho))) {
            return (List<T>) o.readObject();
        } catch (Exception e) {
            return null;
        }
    }

}