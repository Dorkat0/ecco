package at.jku.isse.ecco.storage.neo4j.dao;

import at.jku.isse.ecco.EccoException;
import at.jku.isse.ecco.dao.RepositoryDao;
import at.jku.isse.ecco.dao.TransactionStrategy;
import at.jku.isse.ecco.repository.Repository;
import at.jku.isse.ecco.storage.neo4j.domain.*;
import com.google.inject.Inject;
import org.neo4j.ogm.session.LoadStrategy;
import org.neo4j.ogm.session.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class NeoRepositoryDao extends NeoAbstractGenericDao implements RepositoryDao {
	private NeoRepository repository;

	@Inject
	public NeoRepositoryDao(NeoTransactionStrategy transactionStrategy) {
		super(transactionStrategy);
	}

	@Override
	public Repository.Op load() {
		final Session neoSession = this.transactionStrategy.getNeoSession();
		System.out.println(neoSession);
		System.out.println(transactionStrategy);

		if (this.repository == null) {
			neoSession.setLoadStrategy(LoadStrategy.SCHEMA_LOAD_STRATEGY);
			NeoRepository repo = neoSession.load(NeoRepository.class, 0L);
			neoSession.setLoadStrategy(LoadStrategy.PATH_LOAD_STRATEGY);
			if (repo == null) {
				repo = new NeoRepository(this.transactionStrategy);
				this.repository = repo;
				return repo;
			} else {

				{
					/** load features */
					Iterator<NeoFeature> it = repo.getFeatures().iterator();
					while (it.hasNext()) {
						NeoFeature actFeature = it.next();

						if (actFeature.getNeoId() != null) {
							neoSession.load(NeoFeature.class, actFeature.getNeoId(), 1);
						}
					}
				}

				/** SOBALD ETWAS GELADEN WIRD, WERDEN ANDERE TEILE DES REPOSITORIES WIEDER ENTFERNT - WARUM? */
				//https://stackoverflow.com/questions/56233105/neo4j-ogm-replaces-collections-on-load-losing-already-hydrated-data

				/** load associations */
				neoSession.loadAll(NeoAssociation.class, 4);
				neoSession.loadAll(NeoNode.class, 2);
				neoSession.loadAll(NeoArtifact.class, 2);


				/** load modules */
				// removes modules after loading other items, hence set them manually
				Collection<NeoModule> loadedModules = neoSession.loadAll(NeoModule.class, 1);
				repo.setModules(new ArrayList(loadedModules));


				this.repository = repo;
				return repo;
			}
		} else {
			return this.repository;
		}
	}

	@Override
	public void store(Repository.Op repository) {
		if (this.transactionStrategy.getTransaction() != TransactionStrategy.TRANSACTION.READ_WRITE)
			throw new EccoException("Attempted to store repository without active READ_WRITE transaction.");

		final Session neoSession = this.transactionStrategy.getNeoSession();
		neoSession.save(repository);
	}

}
