package com.oc.projet7api.mapper;

import com.oc.projet7api.model.dto.LoanResponseDTO;
import com.oc.projet7api.model.dto.LoanUserResponseDTO;
import com.oc.projet7api.model.entity.Loan;

public class LoanMapper {
	public static LoanUserResponseDTO toUserResponseDTO(Loan loan) {
		LoanUserResponseDTO dto = new LoanUserResponseDTO();
		
		dto.setId(loan.getId());
		dto.setBookTitle(loan.getBook().getTitle());
		dto.setBookAuthor(loan.getBook().getAuthor());
		dto.setLoanDate(loan.getLoanDate());
		dto.setReturnDate(loan.getReturnDate());
		dto.setReturned(loan.isReturned());
		dto.setExtended(loan.isExtended());
		
		return dto;
	}
	
	public static LoanResponseDTO toResponseDTO(Loan loan) {
		LoanResponseDTO dto = new LoanResponseDTO();
		
		dto.setId(loan.getId());
		dto.setBookTitle(loan.getBook().getTitle());
		dto.setBookAuthor(loan.getBook().getAuthor());
		dto.setUserEmail(loan.getUser().getEmail());
		dto.setUserFirstName(loan.getUser().getFirstName());
		dto.setUserLastName(loan.getUser().getLastName());
		dto.setLoanDate(loan.getLoanDate());
		dto.setReturnDate(loan.getReturnDate());
		dto.setReturned(loan.isReturned());
		dto.setExtended(loan.isExtended());
		
		return dto;
	}
}
