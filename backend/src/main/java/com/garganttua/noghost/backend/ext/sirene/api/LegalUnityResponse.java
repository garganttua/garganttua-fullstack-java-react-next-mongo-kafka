package com.garganttua.noghost.backend.ext.sirene.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class LegalUnityResponse {

    private Header header;
    private UniteLegale uniteLegale;

    @Data
    public static class Header {
        private int statut;
        private String message;
    }

    @Data
    public static class UniteLegale {
        private String siren;
        private String statutDiffusionUniteLegale;
        private LocalDate dateCreationUniteLegale;
        private String sigleUniteLegale;
        private String sexeUniteLegale;
        private String prenom1UniteLegale;
        private String prenom2UniteLegale;
        private String prenom3UniteLegale;
        private String prenom4UniteLegale;
        private String prenomUsuelUniteLegale;
        private String pseudonymeUniteLegale;
        private String identifiantAssociationUniteLegale;
        private String trancheEffectifsUniteLegale;
        private String anneeEffectifsUniteLegale;
        private LocalDateTime dateDernierTraitementUniteLegale;
        private int nombrePeriodesUniteLegale;
        private String categorieEntreprise;
        private String anneeCategorieEntreprise;
        private List<PeriodeUniteLegale> periodesUniteLegale;

        @Data
        public static class PeriodeUniteLegale {
            private LocalDate dateFin;
            private LocalDate dateDebut;
            private String etatAdministratifUniteLegale;
            private boolean changementEtatAdministratifUniteLegale;
            private String nomUniteLegale;
            private boolean changementNomUniteLegale;
            private String nomUsageUniteLegale;
            private boolean changementNomUsageUniteLegale;
            private String denominationUniteLegale;
            private boolean changementDenominationUniteLegale;
            private String denominationUsuelle1UniteLegale;
            private String denominationUsuelle2UniteLegale;
            private String denominationUsuelle3UniteLegale;
            private boolean changementDenominationUsuelleUniteLegale;
            private String categorieJuridiqueUniteLegale;
            private boolean changementCategorieJuridiqueUniteLegale;
            private String activitePrincipaleUniteLegale;
            private String nomenclatureActivitePrincipaleUniteLegale;
            private boolean changementActivitePrincipaleUniteLegale;
            private String nicSiegeUniteLegale;
            private boolean changementNicSiegeUniteLegale;
            private String economieSocialeSolidaireUniteLegale;
            private boolean changementEconomieSocialeSolidaireUniteLegale;
            private String societeMissionUniteLegale;
            private boolean changementSocieteMissionUniteLegale;
            private String caractereEmployeurUniteLegale;
            private boolean changementCaractereEmployeurUniteLegale;
        }
    }
}
