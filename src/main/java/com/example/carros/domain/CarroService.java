package com.example.carros.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.carros.domain.dto.CarroDTO;

@Service
public class CarroService {

	@Autowired
	private CarroRepository carroRepository;
	
	public List<CarroDTO> getCarros(){
		return carroRepository.findAll().stream().map(CarroDTO::criaCarro).collect(Collectors.toList());
	}
	
	public Optional<CarroDTO> getCarroById(Long id) {
		return carroRepository.findById(id).map(CarroDTO::criaCarro);
	}

	public List<CarroDTO> getCarroByTipo(String tipo) {
		return carroRepository.findByTipo(tipo).stream().map(CarroDTO::criaCarro).collect(Collectors.toList());
	}
	
	public CarroDTO insert(Carro carro) {
		Assert.isNull(carro.getId(), "Não foi possível inserir o registro!");
		return  CarroDTO.criaCarro(carroRepository.save(carro));
	}
	
	public CarroDTO atualizarCarro(Carro carro, Long id) {
		 Assert.notNull(id,"Não foi possível atualizar o registro!");
		 
		 //Buscando primeiro o objeto atualizado do db
		 Optional<Carro> optional = carroRepository.findById(id);
		 if(optional.isPresent()) {
			 Carro carroDb = optional.get();
			 //Copia as propriedades
			 carroDb.setNome(carro.getNome());
			 carroDb.setTipo(carro.getTipo());
			 
			 //atualiza o objeto na base
			 carroRepository.save(carroDb);
			 carro = carroDb;
		 }
		return carro.getId() != null ? CarroDTO.criaCarro(carro) : null;
	}
	
	public boolean deletarCarro(Long id) {
		if(getCarroById(id).isPresent()) {
			carroRepository.deleteById(id);
			return true;
		}
		return false;
		
	}
	
	public List<Carro> getCarrosFake(){
		
		List<Carro> carrosList = new ArrayList<Carro>();
		
		carrosList.add(new Carro(1L, "Fusca",""));
		carrosList.add(new Carro(2L, "Gol",""));
		carrosList.add(new Carro(3L, "Jetta",""));
		
		return carrosList;
	}

	
}
