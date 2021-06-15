package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GenerateCriteria {

    //@NonNull
    private List<Criteria> allCriteria;

    public GenerateCriteria(@NonNull List<Criteria> allCriteria) {
        this.allCriteria = allCriteria;
    }
}
