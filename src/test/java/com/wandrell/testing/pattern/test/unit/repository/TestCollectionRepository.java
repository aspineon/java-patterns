/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2014 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.wandrell.testing.pattern.test.unit.repository;

import java.util.Collection;
import java.util.LinkedHashSet;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.wandrell.pattern.repository.CollectionRepository;
import com.wandrell.pattern.repository.Repository;

/**
 * Unit tests for {@link Repository}.
 * <p>
 * Checks the following cases:
 * <ol>
 * <li>Entities are added correctly</li>
 * <li>Entities are removed correctly</li>
 * <li>Entities are updated correctly</li>
 * <li>Updating a non existing entity does not add it</li>
 * <li>The {@code getCollection} method filters the entities correctly</li>
 * <li>Modifying the {@code Collection} returned by {@code getCollection} does
 * not modify the repository's internal collection</li>
 * </ol>
 * 
 * @author Bernardo Martínez Garrido
 * @version 0.1.0
 * @see Repository
 */
public final class TestCollectionRepository {

    /**
     * The repository being tested.
     */
    private Repository<String> repository;

    /**
     * Default constructor.
     */
    public TestCollectionRepository() {
        super();
    }

    /**
     * Restores the repository state before each test.
     */
    @BeforeMethod
    public final void cleanUp() {
        for (final String entity : repository.getCollection(s -> true)) {
            repository.remove(entity);
        }

        repository.add("a");
        repository.add("b");
        repository.add("c");
    }

    /**
     * Creates the repository being tested before any test is run.
     */
    @BeforeClass
    public final void initialize() {
        repository = new CollectionRepository<>(new LinkedHashSet<>());
    }

    /**
     * Tests that entities are added correctly.
     */
    @Test
    public final void testAdd_Adds() {
        final Collection<String> entities; // All the entities

        repository.add("d");

        entities = repository.getCollection(s -> true);

        Assert.assertEquals(entities.size(), 4);
        Assert.assertTrue(entities.contains("d"));
    }

    /**
     * Test that the {@code getCollection} method filters the entities
     * correctly.
     */
    @Test
    public final void testGetCollection_Filter_Filters() {
        final Collection<String> entities; // Filtered entities

        entities = repository.getCollection(s -> s == "b");

        Assert.assertEquals(entities.size(), 1);
        Assert.assertTrue(entities.contains("b"));
    }

    /**
     * Tests that modifying the {@code Collection} returned by
     * {@code getCollection} does not modify the repository's internal
     * collection.
     */
    @Test
    public final void testGetCollection_Remove_OriginalNotChanges() {
        final Collection<String> entities; // Filtered entities

        entities = repository.getCollection(s -> s == "b");

        entities.remove("b");

        Assert.assertEquals(entities.size(), 0);
        Assert.assertEquals(repository.getCollection(s -> true).size(), 3);
    }

    /**
     * Tests that entities are removed correctly.
     */
    @Test
    public final void testRemove_Removes() {
        final Collection<String> entities; // Filtered entities

        repository.remove("b");

        entities = repository.getCollection(s -> true);

        Assert.assertEquals(entities.size(), 2);
        Assert.assertTrue(!entities.contains("b"));
    }

    /**
     * Tests that entities are updated correctly.
     */
    @Test
    public final void testUpdate_Existing_Update() {
        // TODO: Check this with more complex data
        final Collection<String> entities; // All the entities

        repository.update("c");

        entities = repository.getCollection(s -> true);

        Assert.assertEquals(entities.size(), 3);
        Assert.assertTrue(entities.contains("c"));
    }

    /**
     * Tests that updating a non existing entity does not add it.
     */
    @Test
    public final void testUpdate_NotExisting_NoAdd() {
        final Collection<String> entities; // All the entities

        repository.update("d");

        entities = repository.getCollection(s -> true);

        Assert.assertEquals(entities.size(), 3);
        Assert.assertTrue(!entities.contains("d"));
    }

}