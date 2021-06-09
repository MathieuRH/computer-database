package com.excilys.cdb.verification;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTOJsp;
import com.excilys.cdb.exceptions.CompanyFormatException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.exceptions.NameFormatException;

@Component
public class Verificator {
	
	private static final String DATE_FORMAT_TYPE = "Date must be of format yyyy-mm-dd";
	private static final String DATE_LIMITS = "Date must be between 1970-01-01 & 2038-01-19";
	
	private static Logger logger = LoggerFactory.getLogger(Verificator.class);
	
	public void verifyComputer(ComputerDTOJsp computerDTO) throws InputException {
		verifyName(computerDTO.getName());
		verifyIntroduced(computerDTO.getIntroduced());
		verifyDiscontinued(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		verifyCompany(computerDTO.getCompanyId());
	}

	private void verifyName(String name) throws NameFormatException {
		if ("".equals(name)) {
			throw new NameFormatException("Computer name cannot be empty");
		}
	}
	

	private void verifyIntroduced(String introduced) throws DateFormatException{
		if (!"".equals(introduced)) {
			try {
				LocalDate introducedDate = LocalDate.parse(introduced); 
				if (!introducedDate.isAfter(LocalDate.of(1970, 1, 1)) || !introducedDate.isBefore(LocalDate.of(2038,01,19))){
					throw new DateFormatException(DATE_LIMITS);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new DateFormatException(DATE_FORMAT_TYPE);
			}
		}
	}

	private void verifyDiscontinued(String introduced, String discontinued) throws DateFormatException{
		if (!"".equals(discontinued)){
			try {
				LocalDate introducedDate = LocalDate.parse(introduced); 
				LocalDate discontinuedDate = LocalDate.parse(discontinued); 
				if (!discontinuedDate.isAfter(introducedDate)) {
						throw new DateFormatException("Discontinued date must be after introduction date");
					} else if (!discontinuedDate.isAfter(LocalDate.of(1970, 1, 1)) 
							|| !discontinuedDate.isBefore(LocalDate.of(2038,01,19))){
						throw new DateFormatException(DATE_LIMITS);
					}
			} catch (Exception e) {
				logger.error(e.getMessage());
				throw new DateFormatException(DATE_FORMAT_TYPE);
			}
		}
	}

	private void verifyCompany(String companyId) throws CompanyFormatException {
		if (!"".equals(companyId)){
			try {
				int numCompany = Integer.parseInt(companyId);
				if (numCompany<0) {
					throw new CompanyFormatException("Company id must be positive");
				}
			} catch (NumberFormatException e) {
				logger.error(e.getMessage());
				throw new CompanyFormatException("Company id must be of integer type");
			}
		}
	}
}
