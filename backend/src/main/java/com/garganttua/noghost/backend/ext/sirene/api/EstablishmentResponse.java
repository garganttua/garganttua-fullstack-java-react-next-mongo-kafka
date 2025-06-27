package com.garganttua.noghost.backend.ext.sirene.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class EstablishmentResponse {

    private Header header;
    private Etablissement etablissement;

    @Data
    public static class Header {
        private int statut;
        private String message;
    }

    @Data
    public static class Etablissement {
        private String siren;
        private String nic;
        private String siret;
        private String statutDiffusionEtablissement;
        private LocalDate dateCreationEtablissement;
        private String trancheEffectifsEtablissement;
        private String anneeEffectifsEtablissement;
        private String activitePrincipaleRegistreMetiersEtablissement;
        private LocalDateTime dateDernierTraitementEtablissement;
        private boolean etablissementSiege;
        private int nombrePeriodesEtablissement;
        private UniteLegale uniteLegale;
        private AdresseEtablissement adresseEtablissement;
        private Adresse2Etablissement adresse2Etablissement;
        private List<PeriodeEtablissement> periodesEtablissement;

        @Data
        public static class UniteLegale {
            private String etatAdministratifUniteLegale;
            private String statutDiffusionUniteLegale;
            private LocalDate dateCreationUniteLegale;
            private String categorieJuridiqueUniteLegale;
            private String denominationUniteLegale;
            private String sigleUniteLegale;
            private String denominationUsuelle1UniteLegale;
            private String denominationUsuelle2UniteLegale;
            private String denominationUsuelle3UniteLegale;
            private String sexeUniteLegale;
            private String nomUniteLegale;
            private String nomUsageUniteLegale;
            private String prenom1UniteLegale;
            private String prenom2UniteLegale;
            private String prenom3UniteLegale;
            private String prenom4UniteLegale;
            private String prenomUsuelUniteLegale;
            private String pseudonymeUniteLegale;
            private String activitePrincipaleUniteLegale;
            private String nomenclatureActivitePrincipaleUniteLegale;
            private String identifiantAssociationUniteLegale;
            private String economieSocialeSolidaireUniteLegale;
            private String societeMissionUniteLegale;
            private String caractereEmployeurUniteLegale;
            private String trancheEffectifsUniteLegale;
            private String anneeEffectifsUniteLegale;
            private String nicSiegeUniteLegale;
            private LocalDateTime dateDernierTraitementUniteLegale;
            private String categorieEntreprise;
            private String anneeCategorieEntreprise;
        }

        @Data
        public static class AdresseEtablissement {
            private String complementAdresseEtablissement;
            private String numeroVoieEtablissement;
            private String indiceRepetitionEtablissement;
            private String dernierNumeroVoieEtablissement;
            private String indiceRepetitionDernierNumeroVoieEtablissement;
            private String typeVoieEtablissement;
            private String libelleVoieEtablissement;
            private String codePostalEtablissement;
            private String libelleCommuneEtablissement;
            private String libelleCommuneEtrangerEtablissement;
            private String distributionSpecialeEtablissement;
            private String codeCommuneEtablissement;
            private String codeCedexEtablissement;
            private String libelleCedexEtablissement;
            private String codePaysEtrangerEtablissement;
            private String libellePaysEtrangerEtablissement;
            private String identifiantAdresseEtablissement;
            private String coordonneeLambertAbscisseEtablissement;
            private String coordonneeLambertOrdonneeEtablissement;
        }

        @Data
        public static class Adresse2Etablissement {
            private String complementAdresse2Etablissement;
            private String numeroVoie2Etablissement;
            private String indiceRepetition2Etablissement;
            private String typeVoie2Etablissement;
            private String libelleVoie2Etablissement;
            private String codePostal2Etablissement;
            private String libelleCommune2Etablissement;
            private String libelleCommuneEtranger2Etablissement;
            private String distributionSpeciale2Etablissement;
            private String codeCommune2Etablissement;
            private String codeCedex2Etablissement;
            private String libelleCedex2Etablissement;
            private String codePaysEtranger2Etablissement;
            private String libellePaysEtranger2Etablissement;
        }

        @Data
        public static class PeriodeEtablissement {
            private LocalDate dateFin;
            private LocalDate dateDebut;
            private String etatAdministratifEtablissement;
            private boolean changementEtatAdministratifEtablissement;
            private String enseigne1Etablissement;
            private String enseigne2Etablissement;
            private String enseigne3Etablissement;
            private boolean changementEnseigneEtablissement;
            private String denominationUsuelleEtablissement;
            private boolean changementDenominationUsuelleEtablissement;
            private String activitePrincipaleEtablissement;
            private String nomenclatureActivitePrincipaleEtablissement;
            private boolean changementActivitePrincipaleEtablissement;
            private String caractereEmployeurEtablissement;
            private boolean changementCaractereEmployeurEtablissement;
        }
    }
}