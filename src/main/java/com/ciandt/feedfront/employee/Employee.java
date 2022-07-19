package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Employee implements Serializable{
    private final String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static String path = "src/main/resources/";


    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();

        if (nome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        }else {
            this.nome = nome;
        }

        if (sobrenome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");
        }else {
            this.sobrenome = sobrenome;
        }

        this.email = email;
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        try{
            FileOutputStream fos = new FileOutputStream(path + employee.id + ".byte");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employee);
            oos.close();
        }catch (Throwable ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return employee;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException {
        return salvarEmployee(employee);
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        List<Employee> list = new ArrayList<>();
        Stream<Path> paths;
        try {
            paths = Files.walk(Paths.get(path));
            List<String> name = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());
            for (String id:name){
                try {
                    list.add(buscarEmployee(id));
                } catch (EmployeeNaoEncontradoException e) {
                    throw new RuntimeException("Employee nao encontrado");
                }
            }
            paths.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }

        return list;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {

        try {
            ObjectInputStream ois;
            FileInputStream fis;
            fis = new FileInputStream(path + id + ".byte");
            ois = new ObjectInputStream(fis);
            Employee employee = (Employee) ois.readObject();
            ois.close();
            return employee;
        } catch (IOException | ClassNotFoundException e) {
            if(e.getClass().getSimpleName().equals("FileNotFoundException")){
                throw new EmployeeNaoEncontradoException("Employee não existe no repositório");
            }
            throw new ArquivoException("");
        }

    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        try {
            buscarEmployee(id);
            new File(path + id + ".byte").delete();
        } catch (EmployeeNaoEncontradoException e) {
            throw new EmployeeNaoEncontradoException("Employee não existe no repositório");
        }

    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        Employee.path = path;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
