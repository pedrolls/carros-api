package com.example.carros.api;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;



@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {
	
	@Autowired
	private CarroService service;
	
	 @GetMapping
	 public ResponseEntity<List<CarroDTO>> get() {
		 return ResponseEntity.ok(service.getCarros());
	 }
	 
	 @GetMapping("/{id}")
	 public ResponseEntity<CarroDTO> getCarroById(@PathVariable("id") Long id) {
		 Optional<CarroDTO> carro = service.getCarroById(id);
		 
		 return carro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//		 return carro.isPresent() ? 
//			 ResponseEntity.ok(carro.get()) :
//			 ResponseEntity.notFound().build();
		 
//		 if(carro.isPresent()) {
//			 return ResponseEntity.ok(carro.get());
//		 }else {
//			 return ResponseEntity.notFound().build();		 }
		 
	 }
	 
	 @GetMapping("/tipo/{tipo}")
	 public ResponseEntity<List<CarroDTO>> getCarrosByTipo(@PathVariable("tipo") String tipo){
		 List<CarroDTO> carros = service.getCarroByTipo(tipo);
		 
		 return carros.isEmpty() ?
				 ResponseEntity.noContent().build() :
				 ResponseEntity.ok(carros);
	 }
	 
	 @PostMapping
	 public ResponseEntity<CarroDTO> post(@RequestBody Carro carro) {
		 try {
			CarroDTO objeto = service.insert(carro);
			 URI location = getUri(objeto.getId());
			 //Metodo created, passando a location (url) que pode fazer um get do novo objeto na base
			 return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	 }
	 
	 @PutMapping("/{id}")
	 public ResponseEntity<CarroDTO> atualizarCarro(@PathVariable("id") Long id, @RequestBody Carro carro) {
		 CarroDTO c = service.atualizarCarro(carro, id);
		 
		 return c != null ? 
				 ResponseEntity.ok(c) :
				ResponseEntity.notFound().build();
	 }
	 
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	 public ResponseEntity deletarCarro(@PathVariable("id") Long id) {
		boolean ok = service.deletarCarro(id);
		 
		 return ok ? ResponseEntity.ok().build() :
				ResponseEntity.notFound().build();
	 }
	 
	 private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri(); 
	 }
}
