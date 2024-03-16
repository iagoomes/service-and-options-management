package br.com.iagoomes.serviceandoptionsmanagement.infra.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationField {
    private String campo;
    private String mensagem;
}
