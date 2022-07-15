package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.io.*;

public class Employee {
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private String path;

    public static List<Employee> list = new ArrayList<Employee>();


    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.path = "/home/claiver/Documentos/files/" + getId() + ".byte";
    }

    public static void salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        list.add(employee);
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(employee.path));
            bw.write(employee.nome + "\r\n");
            bw.write(employee.sobrenome + "\r\n");
            bw.write(employee.email + "\r\n");
            bw.close();
        }catch (Exception ex){
            return;
        }


    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(employee.path));
            bw.write(employee.nome + "\r\n");
            bw.write(employee.sobrenome + "\r\n");
            bw.write(employee.email + "\r\n");
            bw.close();

            int var = 0;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().contains(employee.id)) {
                    var = i;
                }
            }
            if(list.get(var).equals("")){
                throw new EmployeeNaoEncontradoException("Employee não encontrado");
            }else{

                list.get(var).setNome(employee.nome);
                list.get(var).setSobrenome(employee.sobrenome);
                list.get(var).setEmail(employee.email);
                return list.get(var);
            }

        }catch (Exception ex){
            return null;
        }

    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        return list;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        int var = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().contains(id)) {
                var = i;
            }
        }
        if(list.get(var).equals("")){
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        }else{
            return list.get(var);
        }

    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        int var = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().contains(id)) {
                var = i;
            }
        }
        if(list.get(var).equals("")){
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        }else{
            list.remove(var);
        }
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        if (nome.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        }else{
            this.nome = nome;
        }
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        if(sobrenome.length() <= 2){
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");
        }else{
            this.sobrenome = sobrenome;
        }
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
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
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
