package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.Form.Deficiencia.DeficienciaForm;
import com.example.demo.Form.Pessoa.PessoaForm;
import com.example.demo.Model.Categoria;
import com.example.demo.Model.Deficiencia;
import com.example.demo.Model.Pessoa;
import com.example.demo.Repository.CategoriaRepository;
import com.example.demo.Repository.DeficienciaRepository;
import com.example.demo.Service.DeficienciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeficienciaController {

    @Autowired
    private CategoriaRepository cr;

    @Autowired
    private DeficienciaRepository dr;

    @Autowired
    private DeficienciaService ds;
    @Operation(description = "Busca todas as deficiencias.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "tudo certo!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @GetMapping("/deficiencia")
    public String index(Model model, @RequestParam("display") Optional<String> display) {
        String finalDisplay = display.orElse("true");

        List<Deficiencia> deficiencias = dr.findByAtivo(Boolean.valueOf(finalDisplay));

        model.addAttribute("deficiencias", deficiencias);

        return "deficiencia/listar";
    }
    @Operation(description = "Busca deficiencia que já foi criada.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "tudo certo!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @GetMapping("/deficiencia/create")
    public String create(Model model) {
        DeficienciaForm deficienciaForm = new DeficienciaForm();

        List<Categoria> categorias = cr.findAll();
        deficienciaForm.setListCategorias(categorias);

        model.addAttribute("deficienciaForm", deficienciaForm);

        return "deficiencia/create";
    }
    @Operation(description = "Salvar uma nova deficiência.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "deficiência salva com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Não foi possível salvar."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @PostMapping("/deficiencia/create")
    public String create(@Valid DeficienciaForm deficienciaForm, BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        List<Categoria> categorias = cr.findAll();
        deficienciaForm.setListCategorias(categorias);

        model.addAttribute("deficiencia", deficienciaForm);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/deficiencia/create";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Salvo com sucesso!");
        ds.create(deficienciaForm);

        return "redirect:/deficiencia";
    }
    @Operation(description = "Verifica deficiencia que já foi criada.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Deficiencia checkada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @GetMapping("/deficiencia/update/{id}")
    public String update(@PathVariable Long id, Model model){
        Optional<Deficiencia> defienciencia = dr.findById(id);

        DeficienciaForm df = new DeficienciaForm(defienciencia.orElseThrow());

        List<Categoria> categorias = cr.findAll();
        df.setListCategorias(categorias);

        model.addAttribute("deficienciaForm", df);
        model.addAttribute("id", defienciencia.orElseThrow().getId());

        

        return "/deficiencia/update";
    }
    @Operation(description = "Atualiza deficiencia que já foi criada.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Deficiencia atualizada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @PostMapping("/deficiencia/update/{id}")
    public String update(@PathVariable Long id, @Valid DeficienciaForm deficienciaForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        List<Categoria> categorias = cr.findAll();
        deficienciaForm.setListCategorias(categorias);

        model.addAttribute("deficienciaForm", deficienciaForm);

        if(bindingResult.hasErrors()){
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "/deficiencia/update";
        }

        ds.update(deficienciaForm, id);      

        redirectAttributes.addFlashAttribute("successMessage", "Alterado com sucesso!");
        return "redirect:/deficiencia";
    }
    @Operation(description = "Visualiza a deficiencia pelo seu id.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Deficiencia retornada com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @GetMapping("/deficiencia/visualizar/{id}")
    public String visualizar(@PathVariable Long id, Model model){
        Optional<Deficiencia> defienciencia = dr.findById(id);

        DeficienciaForm df = new DeficienciaForm(defienciencia.orElseThrow());

        List<Categoria> categorias = cr.findAll();
        df.setListCategorias(categorias);

        model.addAttribute("deficienciaForm", df);
        model.addAttribute("id", defienciencia.orElseThrow().getId());

        return "/deficiencia/visualizar";
    }  
    @Operation(description = "Remover uma deficiencia pelo seu id.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Deficiencia removida com sucesso!"),
        @ApiResponse(responseCode = "400", description = "Não encontrado deficiencias."),
        @ApiResponse(responseCode = "404", description = "Página não encontrada.")
    })
    @GetMapping("/deficiencia/remover/{id}")
    public String remover(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Deficiencia> d = this.dr.findById(id);
        Deficiencia deficiencia = d.get();

        if (deficiencia.isAtivo()) {
            deficiencia.setAtivo(false);    
            redirectAttributes.addFlashAttribute("successMessage", 
            "Excluído com sucesso!");
        }else{
            deficiencia.setAtivo(true);
            redirectAttributes.addFlashAttribute("successMessage", 
            "Recuperado com sucesso!");
        }
        
        this.dr.save(deficiencia);
        
        return "redirect:/deficiencia";        
    }
}
