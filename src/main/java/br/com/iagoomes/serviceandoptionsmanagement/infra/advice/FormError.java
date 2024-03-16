package br.com.iagoomes.serviceandoptionsmanagement.infra.advice;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class FormError extends DefaultError {
    private final List<ValidationField> messages = new ArrayList<>();

    public void addMenssagens(String campo, String mensagem) {
        messages.add(new ValidationField(campo, mensagem));
    }
}
