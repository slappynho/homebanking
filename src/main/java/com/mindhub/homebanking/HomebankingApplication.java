package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.*;

@SpringBootApplication
public class HomebankingApplication {


	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}



	@Bean
	public CommandLineRunner initData(ClienteRepository clienteRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository,CardRepository cardRepository){
		return args -> {

			Cliente cliente1 = new Cliente("Melba", "Morel", "morelADMINDHUB@gmail.com",passwordEncoder.encode("melbinha"));
			Cliente cliente2 = new Cliente ("Fabricio", "Gauna", "fabriciogauna16@gmail.com",passwordEncoder.encode("fabrinho"));
			clienteRepository.save(cliente1);
			clienteRepository.save(cliente2);
			Account account1 = new Account ("VIN-001",7000. , LocalDateTime.now(),true);
			Account account2 = new Account ("VIN-002",5000. , LocalDateTime.now().plusDays(1),true);
			Account account3 = new Account ("VIN-003",12000. , LocalDateTime.now(),true);
			Account account4 = new Account ("VIN-004",15000. , LocalDateTime.now().plusDays(1),true);
			cliente1.addAccount(account1);
			cliente1.addAccount(account2);
			cliente2.addAccount(account3);
			cliente2.addAccount(account4);
			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction(account1, TransactionType.DEBIT, -100 , "Netflix", LocalDateTime.now(),account1.getBalance());
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction(account2, TransactionType.CREDIT, 6100 , "Perfume avon", LocalDateTime.now(),account2.getBalance());
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction(account3, TransactionType.DEBIT, -1000 , "Amazon", LocalDateTime.now(),account3.getBalance());
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction(account4, TransactionType.CREDIT, 16100 , "Salario", LocalDateTime.now(),account4.getBalance());
			transactionRepository.save(transaction4);
			Loan loan1 = new Loan( 5000000.00,"Hipotecario", List.of(6, 12, 24, 36, 48, 60));
			Loan loan2 = new Loan( 1000000.00,"Personal", List.of(6, 12, 24));
			Loan loan3 = new Loan( 300000.00, "Vehiculo",List.of(6, 12, 24, 36));
			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			ClientLoan clientLoan1 = new ClientLoan(cliente1, loan1, 60, 400000.00);
			ClientLoan clientLoan2 = new ClientLoan(cliente1, loan2, 12, 50000.00 );
			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			ClientLoan clientLoan3 = new ClientLoan(cliente2, loan2, 24, 100000.00);
			ClientLoan clientLoan4 = new ClientLoan(cliente2, loan3, 36, 200000.00);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			Card card1 = new Card(cliente1,cliente1.getFirstName()+" "+cliente1.getLastName(),CardType.DEBIT,GOLD,"4123 3213 5619 4312",303, LocalDateTime.now(),LocalDateTime.now().plusYears(5), true);
			Card card2 = new Card(cliente1,cliente1.getFirstName()+" "+cliente1.getLastName(),CardType.CREDIT,TITANIUM,"8122 1379 6283 5592",937, LocalDateTime.now(),LocalDateTime.now().plusYears(5), true);
			Card card3 = new Card(cliente2,cliente2.getFirstName()+" "+cliente2.getLastName(),CardType.CREDIT,SILVER,"4123 3213 5619 4312",303, LocalDateTime.now(),LocalDateTime.now().plusYears(5), true);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

		};
	}
}
