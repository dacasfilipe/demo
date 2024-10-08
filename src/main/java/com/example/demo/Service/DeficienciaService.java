package com.example.demo.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.Enum.Sexo;
import com.example.demo.Form.Deficiencia.DeficienciaForm;
import com.example.demo.Form.Pessoa.PessoaForm;
import com.example.demo.Model.Bairro;
import com.example.demo.Model.Cidade;
import com.example.demo.Model.Deficiencia;
import com.example.demo.Model.Endereco;
import com.example.demo.Model.Pessoa;
import com.example.demo.Repository.BairroRepository;
import com.example.demo.Repository.CidadeRepository;
import com.example.demo.Repository.DeficienciaRepository;
import com.example.demo.Repository.EnderecoRepository;
import com.example.demo.Repository.PessoaRepository;

@Service
public class DeficienciaService {

    @Autowired
    private DeficienciaRepository dr;

    public List<Deficiencia> findAll(){
        return dr.findAll();
    }

    public Deficiencia create(DeficienciaForm df){
        Deficiencia d = new Deficiencia();
        d.setNome(df.getNome());
        d.setCategoria(df.getCategoria());

        dr.save(d);
        return d;
    }

    public Deficiencia update(DeficienciaForm df, Long id){    
        Deficiencia deficiencia = this.dr.findById(id).orElseThrow();

        deficiencia.setNome(df.getNome());
        deficiencia.setCategoria(df.getCategoria());

        dr.save(deficiencia);
        return deficiencia;
    }

}