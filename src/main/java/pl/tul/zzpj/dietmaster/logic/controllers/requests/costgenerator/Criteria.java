package pl.tul.zzpj.dietmaster.logic.controllers.requests.costgenerator;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import pl.tul.zzpj.dietmaster.logic.domain.DesiredEffectType;

@Getter
@RequiredArgsConstructor
public class Criteria {

    @NonNull
    private final DesiredEffectType type;

    @NonNull
    private final String value;

    @NonNull
    private final Integer dietCount;

}
